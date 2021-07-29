package com.example.pianoteachingassistant

import android.content.Context
import android.media.MediaPlayer


class NoteThread :Thread(){
    var mediaPlayer: MediaPlayer? = null
    var semantic: String? = null
    var cont: Context? = null

    public override fun run() {
        super.run()
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.reset()
        playNote(this.semantic!!)
    }


    fun playNote(name: String) {

        if(name == "C4"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.c4)
        }else if(name == "D4"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.d4)
        }else if(name == "E4"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.e4)
        }else if(name == "F4"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.f4)
        }else if(name == "G4"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.g4)
        }else if(name == "A4"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.a4)
        }else if(name == "B4"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.b4)
        }else if(name == "C5"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.c5)
        }else if(name == "D5"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.d5)
        }else if(name == "E5"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.e5)
        }else if(name == "F5"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.f5)
        }else if(name == "G5"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.g5)
        }else if(name == "A5"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.a5)
        }else if(name == "E2"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.e2)
        }else if(name == "F2"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.f2)
        }else if(name == "G2"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.g2)
        }else if(name == "A2"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.a2)
        }else if(name == "B2"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.b2)
        }else if(name == "C3"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.c3)
        }else if(name == "D3"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.d3)
        }else if(name == "E3"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.e3)
        }else if(name == "F3"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.f3)
        }else if(name == "G3"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.g3)
        }else if(name == "A3"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.a3)
        }else if(name == "B3"){
            mediaPlayer = MediaPlayer.create(cont, R.raw.b3)
        }
        mediaPlayer!!.start()
    }
}