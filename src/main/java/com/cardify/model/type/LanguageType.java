package com.cardify.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum LanguageType {

    ARABIC("ar", "Arabic", "أعطني مثالاً على استخدام عبارة '%s'"),
    BULGARIAN("bg", "Bulgarian", "Дайте ми един пример за използване на фразата '%s'"),
    CHINESE("zh", "Chinese", "给我一个使用短语'%s'的例子"),
    CROATIAN("hr", "Croatian", "Daj mi jedan primjer korištenja izraza '%s'"),
    CZECH("cs", "Czech", "Uveďte jeden příklad použití fráze '%s'"),
    DANISH("da", "Danish", "Giv mig et eksempel på at bruge udtrykket '%s'"),
    DUTCH("nl", "Dutch", "Geef me een voorbeeld van het gebruik van de uitdrukking '%s'"),
    ENGLISH("en", "English", "Give me one example of using the phrase '%s'"),
    ESTONIAN("et", "Estonian", "Tooge üks näide fraasi '%s' kasutamisest"),
    FINNISH("fi", "Finnish", "Anna yksi esimerkki ilmauksen '%s' käytöstä"),
    FRENCH("fr", "French", "Donnez-moi un exemple d'utilisation de l'expression '%s'"),
    GERMAN("de", "German", "Geben Sie mir ein Beispiel für die Verwendung des Ausdrucks '%s'"),
    GREEK("el", "Greek", "Δώστε μου ένα παράδειγμα χρήσης της φράσης '%s'"),
    GUJARATI("gu", "Gujarati", "મને '%s' વાક્યનો ઉપયોગ કરવાનું એક ઉદાહરણ આપો"),
    HINDI("hi", "Hindi", "मुझे '%s' वाक्यांश का उपयोग करने का एक उदाहरण दें"),
    HUNGARIAN("hu", "Hungarian", "Mondjon egy példát a '%s' kifejezés használatára"),
    IRISH("ga", "Irish", "Tabhair sampla amháin dom den abairt '%s' a úsáid"),
    ITALIAN("it", "Italian", "Fammi un esempio dell'uso della frase '%s'"),
    JAPANESE("ja", "Japanese", "'%s'というフレーズの使用例を 1 つ挙げてください"),
    KOREAN("ko", "Korean", "'%s'라는 문구를 사용하는 한 가지 예를 들어주세요"),
    LATVIAN("lv", "Latvian", "Da mihi unum exemplum, ut hoc dicamus '%s'"),
    LITHUANIAN("lt", "Lithuanian", "Pateikite vieną frazės '%s' naudojimo pavyzdį"),
    NORWEGIAN("no", "Norwegian", "Gi meg ett eksempel på bruk av uttrykket '%s'"),
    POLISH("pl", "Polish", "Podaj mi jeden przykład użycia wyrażenia '%s'"),
    PORTUGUESE("pt", "Portuguese", "Dê-me um exemplo de uso da frase '%s'"),
    ROMANIAN("ro", "Romanian", "Dați-mi un exemplu de utilizare a expresiei '%s'"),
    RUSSIAN("ru", "Russian", "Приведите один пример использования фразы '%s'"),
    SERBIAN("sr", "Serbian", "Дајте ми један пример коришћења израза '%s'"),
    SLOVAK("sk", "Slovak", "Uveďte jeden príklad použitia frázy '%s'"),
    SLOVENIAN("sl", "Slovenian", "Daj mi en primer uporabe izraza '%s'"),
    SPANISH("es", "Spanish", "Dame un ejemplo del uso de la frase '%s'"),
    SWEDISH("sv", "Swedish", "Ge mig ett exempel på hur man använder frasen '%s'"),
    TURKISH("tr", "Turkish", "Bana '%s' ifadesinin kullanımının bir örnek verin"),
    UKRAINIAN("uk", "Ukrainian", "Наведіть один приклад використання фрази '%s'");

    private final String languageCode;
    private final String name;
    private final String phrase;
}
