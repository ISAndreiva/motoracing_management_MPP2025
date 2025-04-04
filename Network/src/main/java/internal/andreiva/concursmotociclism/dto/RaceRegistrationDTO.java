package internal.andreiva.concursmotociclism.dto;

import java.io.Serializable;

public record RaceRegistrationDTO(String racerName, String racerCNP, String teamName,
                                  String raceName) implements Serializable
{
}
