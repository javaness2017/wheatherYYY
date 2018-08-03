package structure;
public class rWheather {
    private double temp;//: 301.01,
    private double temp_min;//": 299.269,
    private double temp_max;//": 301.01,
    private double pressure;//": 1004,
    private double sea_level;//": 1023.18,
    private double grnd_level;//": 1004,
    private double humidity;//": 81,
    private double temp_kf;//": 1.74
    private String dt_txt;//": "2016-07-18 09:00:00"
    private String icon;//": "2016-07-18 09:00:00"

    public double getTemp() {
        return temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getPressure() {
        return pressure;
    }

    public double getSea_level() {
        return sea_level;
    }

    public double getGrnd_level() {
        return grnd_level;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp_kf() {
        return temp_kf;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public String getIcon() {
        return icon;
    }

    public rWheather(double temp, double temp_min, double temp_max, double pressure, double sea_level, double grnd_level, double humidity, double temp_kf, String dt_txt, String icon) {
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
        this.temp_kf = temp_kf;
        this.dt_txt = dt_txt;
        this.temp = temp;
        this.icon = icon;
    }
}
