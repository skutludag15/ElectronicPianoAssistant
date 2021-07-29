package com.example.pianoteachingassistant

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.*


class MusicManager : AppCompatActivity() {
    private val TAG = "MusicManager"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        val playButton = findViewById<Button>(R.id.playButton)
        playButton.setOnClickListener {

            SemanticsConverter.instance.convertSymbols()
            SemanticsConverter.instance.convertStaffLines()
            SemanticsConverter.instance.reconstruct()
            SemanticsConverter.instance.orderingSymbols()
            SemanticsConverter.instance.getSemantics()
            playMusic()
        }
    }

    fun playMusic() {

        for (staffs in SemanticsConverter.instance.container3) {
            var counter = 1
            runBlocking {
                val job = launch {
                    while (counter < 21) {
                        var staffSelected = mutableListOf<ClassWithCoordinates>()
                        if (counter == 1) {
                            staffSelected = staffs.staffBoth.notes1
                        } else if (counter == 2) {
                            staffSelected = staffs.staffBoth.notes2
                        } else if (counter == 3) {
                            staffSelected = staffs.staffBoth.notes3
                        } else if (counter == 4) {
                            staffSelected = staffs.staffBoth.notes4
                        } else if (counter == 5) {
                            staffSelected = staffs.staffBoth.notes5
                        } else if (counter == 6) {
                            staffSelected = staffs.staffBoth.notes6
                        } else if (counter == 7) {
                            staffSelected = staffs.staffBoth.notes7
                        } else if (counter == 8) {
                            staffSelected = staffs.staffBoth.notes8
                        } else if (counter == 9) {
                            staffSelected = staffs.staffBoth.notes9
                        } else if (counter == 10) {
                            staffSelected = staffs.staffBoth.notes10
                        } else if (counter == 11) {
                            staffSelected = staffs.staffBoth.notes11
                        } else if (counter == 12) {
                            staffSelected = staffs.staffBoth.notes12
                        } else if (counter == 13) {
                            staffSelected = staffs.staffBoth.notes13
                        } else if (counter == 14) {
                            staffSelected = staffs.staffBoth.notes14
                        } else if (counter == 15) {
                            staffSelected = staffs.staffBoth.notes15
                        } else if (counter == 16) {
                            staffSelected = staffs.staffBoth.notes16
                        } else if (counter == 17) {
                            staffSelected = staffs.staffBoth.notes17
                        } else if (counter == 18) {
                            staffSelected = staffs.staffBoth.notes18
                        } else if (counter == 19) {
                            staffSelected = staffs.staffBoth.notes19
                        } else if (counter == 20) {
                            staffSelected = staffs.staffBoth.notes20
                        }
                        for (notes in staffSelected) {
                            val sem = notes.noteSemantic
                            val noteT = NoteThread()
                            noteT.semantic = sem
                            noteT.cont = this@MusicManager
                            noteT.start()
                        }

                        counter = counter + 1
                        delay(500L)
                    }
                }
            }
        }
    }

}