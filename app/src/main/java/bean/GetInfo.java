package bean;

public class GetInfo {
    private String name;
    private String url;
    private String genre;
    private String time;

    public GetInfo(String name, String url, String genre, String time) {
        this.name = name;
        this.url = url;
        this.genre = genre;
        this.time = time;
    }

    public GetInfo(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "GetInfo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", genre='" + genre + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
