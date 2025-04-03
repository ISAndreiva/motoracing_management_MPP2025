package internal.andreiva.concursmotociclism.dto;

import java.io.Serializable;

public class RaceRegistrationDTO implements Serializable
{
    private final String racerName;
    private final String racerCNP;
    private final String teamName;
    private final String raceName;

    public RaceRegistrationDTO(String racerName, String racerCNP, String teamName, String raceName)
    {
        this.racerName = racerName;
        this.racerCNP = racerCNP;
        this.teamName = teamName;
        this.raceName = raceName;
    }

    public String getRacerName()
    {
        return racerName;
    }

    public String getRacerCNP()
    {
        return racerCNP;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public String getRaceName()
    {
        return raceName;
    }
}
