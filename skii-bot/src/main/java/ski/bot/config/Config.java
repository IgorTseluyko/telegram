package ski.bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class Config {

    private String telegramUrl, token, jasnaSnow, jasnaWeather, zakopaneSnow, zakopaneWeather;
    private int timeOut, poolSize, maxConn, updateInterval;

    public String getTelegramUrl() {
        return telegramUrl;
    }

    public String getToken() {
        return token;
    }

    public String getJasnaSnow() {
        return jasnaSnow;
    }

    public String getJasnaWeather() {
        return jasnaWeather;
    }

    public String getZakopaneSnow() {
        return zakopaneSnow;
    }

    public String getZakopaneWeather() {
        return zakopaneWeather;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setTelegramUrl(String telegramUrl) {
        this.telegramUrl = telegramUrl;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setJasnaSnow(String jasnaSnow) {
        this.jasnaSnow = jasnaSnow;
    }

    public void setJasnaWeather(String jasnaWeather) {
        this.jasnaWeather = jasnaWeather;
    }

    public void setZakopaneSnow(String zakopaneSnow) {
        this.zakopaneSnow = zakopaneSnow;
    }

    public void setZakopaneWeather(String zakopaneWeather) {
        this.zakopaneWeather = zakopaneWeather;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    @Override
    public String toString() {
        return "Config{" +
                "telegramUrl='" + telegramUrl + '\'' +
                ", token='" + token + '\'' +
                ", jasnaSnow='" + jasnaSnow + '\'' +
                ", jasnaWeather='" + jasnaWeather + '\'' +
                ", zakopaneSnow='" + zakopaneSnow + '\'' +
                ", zakopaneWeather='" + zakopaneWeather + '\'' +
                ", timeOut=" + timeOut +
                ", poolSize=" + poolSize +
                ", maxConn=" + maxConn +
                ", updateInterval=" + updateInterval +
                '}';
    }
}
