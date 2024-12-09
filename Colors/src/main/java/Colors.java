

public interface Colors
{
    String GREEN = "\u001b[32m";
    String RED = "\u001b[31m";
    String YELLOW = "\u001b[33m";
    String BLUE = "\u001b[34m";
    String BLACK = "\u001b[30m";
    String PURPURE = "\u001b[35m";
    String AZURE = "\u001b[36m";
    String WHITE = "\u001b[37m";
    String NOCOLOR = "\u001b[0m";
    String [ ] colors_array = { GREEN, RED, YELLOW, BLUE, BLACK, PURPURE, AZURE, WHITE, NOCOLOR };

    String BLACKBACKGROUND = "\u001b[40m";
    String REDBACKGROUND = "\u001b[41m";
    String GREENBACKGROUND = "\u001b[42m";
    String YELLOWBACKGROUND = "\u001b[43m";
    String BLUEBACKGROUND = "\u001b[44m";
    String PURPUREBACKGROUND = "\u001b[45m";
    String AZUREBACKGROUND = "\u001b[46m";
    String WHITEBACKGROUND = "\u001b[47m";
    String [ ] back_colors_array = { GREENBACKGROUND, REDBACKGROUND, YELLOWBACKGROUND, BLUEBACKGROUND, BLACKBACKGROUND, PURPUREBACKGROUND, AZUREBACKGROUND, WHITEBACKGROUND };
}

