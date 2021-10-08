package iipscada.config;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import iipscada.annotation.Job;
import iipscada.annotation.TimeJob;
import iipscada.jobs.ClearShareTimeJob;
import iipscada.model.ScadaConfigM;
import iipscada.util.TimeJobManager;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.*;

import static iipscada.config.ScadaConst.*;


/**
 * 初始化Scada
 *
 * @Auther: mao
 * @Date: 18-11-29 00:02
 */
public final class ScadaInit {
    private ScadaInit() {
        throw new RuntimeException("请不要创建ScadaInit");
    }

    public static void init() {
        try {
            initIIPScadaRoalAuth();
            initSysConfig();
            initTimeJob();
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.exit(5);
        }

    }

    /**
     * 初始化时间任务
     */
    public static void initTimeJob() {
        System.out.println("初始化定时任务.....");
        String pakage = "iipscada.jobs";
        File file = new File(ScadaInit.class.getResource("/").getPath() + "/" + pakage.replace(".", "/"));
        if (!file.exists())
            throw new RuntimeException("定时任务文件夹不存在");
        if (!file.isDirectory())
            System.out.println("制定路径不是文件夹");
        File[] files = file.listFiles(t -> t.getName().endsWith(".class"));
        if (!Objects.isNull(files))
            for (File f : files) {
                try {
                    Class clazz = Class.forName(pakage + "." + f.getName().replace(".class", ""));
                    if (clazz.isAnnotationPresent(Job.class) && TimeJob.class.isAssignableFrom(clazz))
                        TimeJobManager.addJob(clazz);
                } catch (ClassNotFoundException e) {
                    System.err.println("加载【" + f.getName() + "】失败");
                }
            }
        System.out.println("初始化定时任务完毕.....");
    }

    /**
     * 初始化系统配置
     */
    public static void initSysConfig() {
        System.out.println("初始化系统配置.....");
        SYS_CONFIG.clear();
        List<ScadaConfigM> list = ScadaConfigM.dao.findAll();
        list.forEach((e) -> SYS_CONFIG.put(e.get("key"), e.get("value")));
    }

    /**
     * 初始化Scada权限
     **/
    public static void initIIPScadaRoalAuth() {
        //初始化角色权限
        System.out.println("初始化角色权限...");
        ROLE_AUTH.clear();
        List<Record> authList = Db.find("SELECT b.*,a.rid,c.auth as mauth FROM sys_role_btn a,sys_btn b,sys_menu c WHERE a.bid=b.id and c.id=b.menuid");
        Map<Long, List<AUTH>> roleAuth = new HashMap<>();

        for (Record auth : authList) {
            long rid = auth.getLong("rid"),
                    mid = auth.getLong("menuid");
            String mAuthStr = auth.get("mauth"), bAuth = auth.get("auth");
            if (mAuthStr == null || mAuthStr.equals(""))
                continue;
            List<AUTH> tmpAuthList = null;
            AUTH mAuth = new AUTH(mAuthStr, mid);

            if (roleAuth.containsKey(rid)) {
                tmpAuthList = roleAuth.get(rid);
            } else {
                tmpAuthList = new ArrayList<>();
                roleAuth.put(rid, tmpAuthList);
            }

            //菜单是否已经存在
//            for (AUTH mAuthTmp : tmpAuthList) {
//                if (mAuthTmp.getMid() == mid) {
//                    mAuth = mAuthTmp;
//                    break;
//                }
//            }
            Optional<AUTH> au = tmpAuthList.stream().filter((mAuthTmp) -> mAuthTmp.getMid() == mid).findFirst();
            if (au.isPresent())
                mAuth = au.get();

            if (bAuth.equals(""))
                continue;
            mAuth.addAuth(bAuth);
            tmpAuthList.add(mAuth);
        }


        //开始赋予权限
        for (Map.Entry<Long, List<AUTH>> auth : roleAuth.entrySet()) {
            Set<String> roleAuthList = new HashSet<>();
            List<AUTH> authItems = auth.getValue();
            long rid = auth.getKey();
            for (AUTH authItem : authItems) {
                String[] conAuths = authItem.getConAuth().split(";");
                List<String> btnAuthList = authItem.getBtnAuthList();
                for (String cAuth : conAuths) {
                    if (cAuth.equals(""))
                        continue;
                    if (!roleAuthList.contains(cAuth))
                        roleAuthList.add(cAuth);
                    for (String sigleAuth : btnAuthList) {
                        String[] btnAuths = sigleAuth.split(";");
                        for (String btnAuth : btnAuths) {
                            roleAuthList.add(cAuth + btnAuth);
                        }
                    }

                }
            }
            ROLE_AUTH.put(rid, roleAuthList);
        }


        System.out.println("初始化角色权限完成...");
    }
}
