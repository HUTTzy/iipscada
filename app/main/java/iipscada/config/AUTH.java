package iipscada.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限类
 *
 * @Auther: mao
 * @Date: 18-11-29 00:11
 */
public class AUTH {
    //角色权限
    private final long mid;
    private final String conAuth;
    private List<String> btnAuthList = new ArrayList<>();

    public AUTH(String conAuth, long mid) {
        this.conAuth = conAuth;
        this.mid = mid;
    }

    public List<String> getBtnAuthList() {
        return btnAuthList;
    }

    public String getConAuth() {
        return conAuth;
    }

    public long getMid() {
        return mid;
    }

    public AUTH addAuth(String auth) {
        btnAuthList.add(auth);
        return this;
    }

    @Override
    public String toString() {
        return String.format("{mid:%d,conAuth:%s,btnAuthList:" + btnAuthList + "}", mid, conAuth);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AUTH) {
            AUTH tmp = (AUTH) obj;
            return mid == tmp.mid &&
                    tmp.conAuth.equals(conAuth)
                    && btnAuthList.equals(tmp.btnAuthList);
        }
        return false;
    }
}
