package iipscada.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import iipscada.paginate.PaginateModel;
import iipscada.util.Iterables;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: mao
 * @Date: 18-10-20 11:42
 */
public class IIPScada extends PaginateModel<IIPScada> {
    public static IIPScada dao = new IIPScada();


    @Override
    public String[] gridAttr() {
        String[] attrs = {"id", "filename", "info", "dataRate", "animRate", "isPannable", "isMovable", "autoSize", "time","hide"};
        return attrs;
    }

    /**
     * 获取所有scada
     **/
    public List<IIPScada> findAllByUid(long uid) {
        return find("select * from me_scada where uid=?", uid);
    }

    /**
     * 通过文件名查找
     **/
    public IIPScada findByFileName(String fileName) {
        return findFirst("select * from me_scada where filename=?", fileName);
    }

    /**
     * 通过文件名,uid查找
     **/
    public IIPScada findByFileNameAndUid(String fileName, long uid) {
        return findFirst("select * from me_scada where filename=? and uid=?", fileName, uid);
    }

    /**
     * 删除一个用户的模板
     */
    public int deleteByFilenameAndUid(String filename, long uid) {
        return Db.delete("delete from me_scada where filename = ? AND uid=?", filename, uid);
    }

    /**
     * 获取用户的scada数量
     */
    public int getCountOfScada(ScadaUser user) {
        return findAllByUid(user.id()).size();
    }

    /***
     * 保存scada
     */
    public boolean save(ScadaUser user) {
        if (getCountOfScada(user) >= user.maxscada()) {
            return false;
        }
        return super.save();
    }

    /**
     * 获取scadalist
     */
    public List<IIPScada> getjsonFiles(ScadaUser user) {
        List<IIPScada> scadaList = dao.findAllByUid(user.id());
        for (int i = 0; i < scadaList.size(); i++) {
            IIPScada tmpScada = scadaList.get(i);
            tmpScada.put("name", tmpScada.get("filename"));
            tmpScada.put("size", String.format("%.2f", tmpScada.getStr("json").length() * 2 / 1024.0) + "KB");
            tmpScada.keep("name", "time", "size");
        }
        return scadaList;
    }

    /**
     * 获取scada名称列表
     */
    public List getFileList(ScadaUser user) {
        List<IIPScada> scadaList = dao.findAllByUid(user.id());
        List<String> re = new ArrayList<>();
        Iterables.forEach(scadaList, (index, item) -> {
            re.add(item.getStr("filename"));
        });
        return re;
    }

    /**
     * 修改scada名称
     */
    public boolean updateFileName(long uid, String newfilename, String filename) {
        if (findByFileNameAndUid(newfilename, uid) == null) {
            return Db.update("update me_scada set filename=? where filename = ? AND uid=?", newfilename, filename, uid) > 0;
        }
        return false;

    }
}
