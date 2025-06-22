#include <windows.h>
#include <mmsystem.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>

/**
 * Schaut ob ein Error auftrat, wenn dies der Fall ist wird ggf. ein ErrorString ausgegeben und das Programm beendet.
 * 
 * \param value Der zu überprüfende Wert.
 * \return EXIT_SUCCESS Wenn kein Fehler auftrat.
 */
void handle_errors(DWORD value) {

    if(value == 0){
        return ;
    }

    char errorText[128];
    BOOL err_code_availible = mciGetErrorString(value, errorText, sizeof(errorText));
    if (err_code_availible == TRUE) {
        printf("[-] MCI-Error: %s", value);

        exit(1);

    }

    else {  
        printf("[-] Unbekannter MCI-Error: %s");

        exit(1);
    }
}

/**
 * Spielt die MP3_Datei über das Windows-Multimediatool ab.
 * 
 * \param file_path Der Dateipfad der MP3_Datei.
 * \return EXIT_SUCCESS Wenn das Abspielen erfolgreich war.
 */
__declspec (dllexport) int __cdecl play_mp3(char file_path[]) {

    //open "<file>" type <device> alias <name>
    //play <alias> [wait]
    DWORD return_value;
    char command[512];
    sprintf(command, "open \"%s\" type mpegvideo alias mmsystem", file_path);
    return_value = mciSendString(command, NULL, 0, NULL);
    handle_errors(return_value);
    mciSendString("play mmsystem wait", NULL, 0, NULL);
    handle_errors(return_value);
    mciSendString("close mmsystem", NULL, 0, NULL);
    handle_errors(return_value);

    return EXIT_SUCCESS;

}

bool WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvreserved) {
    return TRUE;
}