#include <windows.h>
#include <stdbool.h>
#include <mmsystem.h>
#include <windowsx.h>





__declspec (dllexport) bool __cdecl play_wav(char *wav_path) {
    bool sound = PlaySoundA(TEXT(wav_path), NULL, SND_FILENAME | SND_SYNC );
    return sound;
}


BOOL WINAPI DllMain(HINSTANCE instance, DWORD reason, LPVOID buffer) {
    return TRUE;
}
