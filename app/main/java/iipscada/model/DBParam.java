package iipscada.model;

/**
 * 创建DB参数
 *
 * @Auther: mao
 * @Date: 18-10-24 10:32
 */
public class DBParam {
    private String url = "";
    private String name = "";
    private String pwd = "";
    /**
     * query查询语句
     **/
    private String query;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String url() {
        return url;
    }

    public String name() {
        return null == name ? "root" : name;
    }

    public String pwd() {
        return null == pwd ? "" : pwd;
    }

    public String query() {
        return query;
    }

    /**
     * 不带table
     *
     * @param url
     * @param name
     * @param pwd
     */
    public DBParam(String url, String name, String pwd) {
        this.url = url;
        this.name = name;
        this.pwd = pwd;
        this.query = "";
    }

    /**
     * 带table
     *
     * @param url
     * @param name
     * @param pwd
     */
    public DBParam(String url, String name, String pwd, String query) {
        this.url = url;
        this.name = name;
        this.pwd = pwd;
        this.query = query;
    }

    @Override
    public String toString() {
        String str = "{\"url\":\"%s\",\"name\":\"%s\"\"pwd\":\"%s\"\"query\":\"%s\"}";
        return String.format(str, url, name, pwd, query);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DBParam))
            return false;

        DBParam o = (DBParam) obj;
        return o.url.equals(url) && o.pwd.equals(pwd) && o.name.equals(name) && o.query.equals(query);
    }
}
