package com.cardify.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "transcript")
@XmlAccessorType(XmlAccessType.FIELD)
public class YouTubeTranscriptResponse {

    @XmlElement(name = "text")
    private List<Text> texts;

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Text {

        @XmlAttribute(name = "start")
        private Double start;

        @XmlValue
        private String text;
    }
}
