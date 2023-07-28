package model;

public class Weather {
    private WeatherType currentWeather;

    // Hava durumunu rastgele belirlemek için kullanılacak hava durumu listesi
    private final WeatherType[] weatherList = {WeatherType.NORMAL, WeatherType.SUNNY, WeatherType.RAINY, WeatherType.ELECTRIC_STORM, WeatherType.EARTHQUAKE};

    // Rastgele hava durumu belirleyen metot
    public void randomizeWeather() {
        int randomIndex = (int) (Math.random() * weatherList.length);
        currentWeather = weatherList[randomIndex];
    }

    // Hava durumu adını döndüren metot
    public String getCurrentWeatherName() {
        return currentWeather.name();
    }

    // Getter metodu
    public WeatherType getCurrentWeather() {
        return currentWeather;
    }

    // Setter metodu
    public void setCurrentWeather(WeatherType currentWeather) {
        this.currentWeather = currentWeather;
    }
}
