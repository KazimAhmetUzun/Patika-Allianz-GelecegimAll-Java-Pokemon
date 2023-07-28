package model;

public enum WeatherType {
    NORMAL, // Normal hava durumu (diğer hava durumlarına karşı savunma etkisi yok)
    SUNNY, // Güneşli hava durumu (Fire türündeki Pokemonlar için güçlendirme etkisi)
    RAINY, // Yağmurlu hava durumu (Water türündeki Pokemonlar için güçlendirme etkisi)
    ELECTRIC_STORM, // Elektrik fırtınası (Electric türündeki Pokemonlar için güçlendirme etkisi)
    EARTHQUAKE, // Deprem (Earth türündeki Pokemonlar için güçlendirme etkisi)
    CLOUDY
}
