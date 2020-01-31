package bean;

public class GetUrl {
    private String m3u8url;
    private String onlineurl;
    private String download;
    private String num;

    public GetUrl(String m3u8url, String onlineurl, String download, String num) {
        this.m3u8url = m3u8url;
        this.onlineurl = onlineurl;
        this.download = download;
        this.num = num;
    }

    public GetUrl(){

    }

    public String getM3u8url() {
        return m3u8url;
    }

    public void setM3u8url(String m3u8url) {
        this.m3u8url = m3u8url;
    }

    public String getOnlineurl() {
        return onlineurl;
    }

    public void setOnlineurl(String onlineurl) {
        this.onlineurl = onlineurl;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "GetUrl{" +
                "m3u8url='" + m3u8url + '\'' +
                ", onlineurl='" + onlineurl + '\'' +
                ", download='" + download + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
