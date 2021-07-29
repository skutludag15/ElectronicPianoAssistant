package com.example.pianoteachingassistant

import kotlin.math.abs


class SemanticsConverter private constructor() {

    val classNames = arrayOf<String>(
        "accidentalDoubleFlat",
        "accidentalDoubleSharp",
        "accidentalFlat",
        "accidentalFlatSmall",
        "accidentalNatural",
        "accidentalNaturalSmall",
        "accidentalSharp",
        "accidentalSharpSmall",
        "arpeggiato",
        "articAccentAbove",
        "articAccentBelow",
        "articMarcatoAbove",
        "articMarcatoBelow",
        "articStaccatissimoAbove",
        "articStaccatissimoBelow",
        "articStaccatoAbove",
        "articStaccatoBelow",
        "articTenutoAbove",
        "articTenutoBelow",
        "augmentationDot",
        "brace",
        "cClefAlto",
        "cClefAltoChange",
        "cClefTenor",
        "cClefTenorChange",
        "caesura",
        "clef15",
        "clef8",
        "coda",
        "dynamicFF",
        "dynamicFFF",
        "dynamicFFFF",
        "dynamicFFFFF",
        "dynamicForte",
        "dynamicFortePiano",
        "dynamicMF",
        "dynamicMP",
        "dynamicMezzo",
        "dynamicPP",
        "dynamicPPP",
        "dynamicPPPP",
        "dynamicPPPPP",
        "dynamicPiano",
        "dynamicRinforzando2",
        "dynamicSforzando1",
        "dynamicSforzato",
        "fClef",
        "fClefChange",
        "fermataAbove",
        "fermataBelow",
        "fingering0",
        "fingering1",
        "fingering2",
        "fingering3",
        "fingering4",
        "fingering5",
        "flag128thDown",
        "flag128thUp",
        "flag16thDown",
        "flag16thUp",
        "flag32ndDown",
        "flag32ndUp",
        "flag64thDown",
        "flag64thUp",
        "flag8thDown",
        "flag8thUp",
        "gClef",
        "gClefChange",
        "graceNoteAcciaccaturaStemDown",
        "graceNoteAcciaccaturaStemUp",
        "graceNoteAppoggiaturaStemDown",
        "graceNoteAppoggiaturaStemUp",
        "keyFlat",
        "keyNatural",
        "keySharp",
        "keyboardPedalPed",
        "keyboardPedalUp",
        "noteheadBlack",
        "noteheadBlackSmall",
        "noteheadDoubleWhole",
        "noteheadHalf",
        "noteheadHalfSmall",
        "noteheadWhole",
        "noteheadWholeSmall",
        "ornamentMordent",
        "ornamentTrill",
        "ornamentTurn",
        "ornamentTurnInverted",
        "repeatDot",
        "rest128th",
        "rest16th",
        "rest32nd",
        "rest64th",
        "rest8th",
        "restDoubleWhole",
        "restHBar",
        "restHalf",
        "restLonga",
        "restQuarter",
        "restWhole",
        "segno",
        "stringsDownBow",
        "stringsUpBow",
        "timeSig0",
        "timeSig1",
        "timeSig2",
        "timeSig3",
        "timeSig4",
        "timeSig5",
        "timeSig6",
        "timeSig7",
        "timeSig8",
        "timeSig9",
        "timeSigCommon",
        "timeSigCutCommon",
        "tuplet3",
        "tuplet6",
        "unpitchedPercussionClef1"
    )
    var container = mutableListOf<ClassWithCoordinates>()
    var container2 = mutableListOf<StaffLines>()
    var container3 = mutableListOf<PianoStaffs>()

    private val TAG = "Semantics"

    fun convertSymbols() {

        var lstValues = mutableListOf<String>()
        lstValues = Data().gutenberg1949_page1.split("\n") as MutableList<String>

        lstValues.forEach {
            val coordinatesContainer = it.split(" ").map { it -> it.trim() }
            val obj = ClassWithCoordinates()
            var counter: Int = 0
            for (items in coordinatesContainer) {
                if (counter == 0) {

                    obj.cName = classNames[items.toInt()]

                } else if (counter == 1) {

                    obj.xPos = items.toDouble() * 640

                } else if (counter == 2) {

                    obj.yPos = items.toDouble() * 640

                } else if (counter == 3) {

                    obj.xLen = items.toDouble() * 640

                } else if (counter == 4) {

                    obj.yLen = items.toDouble() * 640

                }
                counter = counter + 1;
            }
            container.add(obj)

        }
    }

    fun convertStaffLines() {

        var staffList = mutableListOf<String>()
        staffList = Data().gutenberg1949_page1_staff.split(", ") as MutableList<String>
        for (staffLine in staffList) {

            val staf = StaffLines()
            staf.staff0 = staffLine.toDouble() - 12.0
            staf.staff1 = staffLine.toDouble() - 8.0
            staf.staff2 = staffLine.toDouble() - 4.0
            staf.staff3 = staffLine.toDouble()
            staf.staff4 = staffLine.toDouble() + 4.0
            staf.staff5 = staffLine.toDouble() + 8.0
            staf.staff6 = staffLine.toDouble() + 12.0
            container2.add(staf)
        }

    }

    fun reconstruct() {
        for (symbols in container) {
            val y = symbols.yPos
            for (staff in container2) {

                if (symbols.cName == "fClef") {
                    if (abs(staff.staff3 - y) <= 20.0) {

                        staff.clef = "f"
                        staff.clefObject = symbols
                        staff.symbols.add(symbols)

                        if (symbols.staff == null) {
                            symbols.staff = staff
                        } else {
                            if (abs(symbols!!.staff!!.staff3 - y) < abs(staff.staff3 - y)) {

                            } else {
                                symbols.staff = staff
                            }
                        }
                    }


                } else if (symbols.cName == "gClef") {

                    if (abs(staff.staff3 - y) <= 20.0) {

                        staff.clef = "g"
                        staff.clefObject = symbols
                        staff.symbols.add(symbols)
                        if (symbols.staff == null) {
                            symbols.staff = staff
                        } else {
                            if (abs(symbols!!.staff!!.staff3 - y) < abs(staff.staff3 - y)) {

                            } else {
                                symbols.staff = staff
                            }
                        }
                    }
                } else {

                    if (abs(staff.staff3 - y) <= 25.0) {
                        staff.symbols.add(symbols)
                        if (symbols.staff == null) {
                            symbols.staff = staff
                        } else {
                            if (abs(symbols!!.staff!!.staff3 - y) < abs(staff.staff3 - y)) {

                            } else {
                                symbols.staff = staff
                            }
                        }

                    }
                }
            }

        }
    }

    fun orderingSymbols() {
        var count: Int = 0
        val piano = PianoStaffs()
        var pianoNum: Int = 0
        for (staff in container2) {
            count = count + 1
            if (staff.clef == "g") {

                piano.staffGClef = staff
                piano.valid = 1
                staff.staff3 = staff.clefObject!!.yPos
                staff.staff2 = staff.clefObject!!.yPos - 4
                staff.staff4 = staff.clefObject!!.yPos + 4
                staff.staff1 = staff.clefObject!!.yPos - 8
                staff.staff5 = staff.clefObject!!.yPos + 8
                staff.staff0 = staff.clefObject!!.yPos - 12
                staff.staff6 = staff.clefObject!!.yPos + 12

            } else if (staff.clef == "f") {
                if (piano.valid == 1) {

                    piano.staffFClef = staff
                    piano.valid = 0
                    pianoNum = pianoNum + 1
                    container3.add(piano)
                    piano.staffBoth = StaffLines()
                }

                staff.staff3 = staff.clefObject!!.yPos + 2
                staff.staff2 = staff.clefObject!!.yPos - 2
                staff.staff4 = staff.clefObject!!.yPos + 6
                staff.staff1 = staff.clefObject!!.yPos - 6
                staff.staff5 = staff.clefObject!!.yPos + 10
                staff.staff0 = staff.clefObject!!.yPos - 10
                staff.staff6 = staff.clefObject!!.yPos + 14

            } else {

                piano.valid = 0
                piano.staffGClef = null

            }
            for (sym in staff.symbols) {

                if (sym.cName == "keySharp" || sym.cName == "keyFlat") {
                    getKeyPosition(staff, sym)
                } else if (sym.cName == "accidentals") {

                } else if (sym.cName == "rest") {

                } else {

                    getNotePosition(staff, sym)

                }
            }

        }
    }

    fun getKeyPosition(staff: StaffLines, sym: ClassWithCoordinates) {

        if (sym.cName == "keyFlat") {

            if (sym.yPos < staff.staff0 + 1) {

                sym.noteSemantic = "0 Flat"
                staff.keyFLat.add(0)

            } else if (staff.staff0 + 1 <= sym.yPos && sym.yPos < staff.staff1 - 1) {

                sym.noteSemantic = "1 Flat"
                staff.keyFLat.add(1)

            } else if (staff.staff1 - 1 <= sym.yPos && sym.yPos < staff.staff1 + 1) {

                sym.noteSemantic = "2 Flat"
                staff.keyFLat.add(2)

            } else if (staff.staff1 + 1 <= sym.yPos && sym.yPos < staff.staff2 - 1) {

                sym.noteSemantic = "3 Flat"
                staff.keyFLat.add(3)

            } else if (staff.staff2 - 1 <= sym.yPos && sym.yPos < staff.staff2 + 1) {

                sym.noteSemantic = "4 Flat"
                staff.keyFLat.add(4)

            } else if (staff.staff2 + 1 <= sym.yPos && sym.yPos < staff.staff3 - 1) {

                sym.noteSemantic = "5 Flat"
                staff.keyFLat.add(5)

            } else if (staff.staff3 - 1 <= sym.yPos && sym.yPos < staff.staff3 + 1) {

                sym.noteSemantic = "6 Flat"
                staff.keyFLat.add(6)

            } else if (staff.staff3 + 1 <= sym.yPos && sym.yPos < staff.staff4 - 1) {

                sym.noteSemantic = "7 Flat"
                staff.keyFLat.add(7)

            } else if (staff.staff4 - 1 <= sym.yPos && sym.yPos < staff.staff4 + 1) {

                sym.noteSemantic = "8 Flat"
                staff.keyFLat.add(8)

            } else if (staff.staff4 + 1 <= sym.yPos && sym.yPos < staff.staff5 - 1) {

                sym.noteSemantic = "9 Flat"
                staff.keyFLat.add(9)

            } else if (staff.staff5 - 1 <= sym.yPos && sym.yPos < staff.staff5 + 1) {

                sym.noteSemantic = "10 Flat"
                staff.keyFLat.add(10)

            } else if (staff.staff5 + 1 <= sym.yPos && sym.yPos < staff.staff6 - 1) {

                sym.noteSemantic = "11 Flat"
                staff.keyFLat.add(11)

            } else if (staff.staff6 - 1 <= sym.yPos) {

                sym.noteSemantic = "12 Flat"
                staff.keyFLat.add(12)

            }

        } else {

            if (sym.yPos < staff.staff0 + 1) {

                sym.noteSemantic = "0 Sharp"
                staff.keySharp.add(0)

            } else if (staff.staff0 + 1 <= sym.yPos && sym.yPos < staff.staff1 - 1) {

                sym.noteSemantic = "1 Sharp"
                staff.keySharp.add(1)

            } else if (staff.staff1 - 1 <= sym.yPos && sym.yPos < staff.staff1 + 1) {

                sym.noteSemantic = "2 Sharp"
                staff.keySharp.add(2)

            } else if (staff.staff1 + 1 <= sym.yPos && sym.yPos < staff.staff2 - 1) {

                sym.noteSemantic = "3 Sharp"
                staff.keySharp.add(3)

            } else if (staff.staff2 - 1 <= sym.yPos && sym.yPos < staff.staff2 + 1) {

                sym.noteSemantic = "4 Sharp"
                staff.keySharp.add(4)

            } else if (staff.staff2 + 1 <= sym.yPos && sym.yPos < staff.staff3 - 1) {

                sym.noteSemantic = "5 Sharp"
                staff.keySharp.add(5)

            } else if (staff.staff3 - 1 <= sym.yPos && sym.yPos < staff.staff3 + 1) {

                sym.noteSemantic = "6 Sharp"
                staff.keySharp.add(6)

            } else if (staff.staff3 + 1 <= sym.yPos && sym.yPos < staff.staff4 - 1) {

                sym.noteSemantic = "7 Sharp"
                staff.keySharp.add(7)

            } else if (staff.staff4 - 1 <= sym.yPos && sym.yPos < staff.staff4 + 1) {

                sym.noteSemantic = "8 Sharp"
                staff.keySharp.add(8)

            } else if (staff.staff4 + 1 <= sym.yPos && sym.yPos < staff.staff5 - 1) {

                sym.noteSemantic = "9 Sharp"
                staff.keySharp.add(9)

            } else if (staff.staff5 - 1 <= sym.yPos && sym.yPos < staff.staff5 + 1) {

                sym.noteSemantic = "10 Sharp"
                staff.keySharp.add(10)

            } else if (staff.staff5 + 1 <= sym.yPos && sym.yPos < staff.staff6 - 1) {

                sym.noteSemantic = "11 Sharp"
                staff.keySharp.add(11)

            } else if (staff.staff6 - 1 <= sym.yPos) {

                sym.noteSemantic = "12 Sharp"
                staff.keySharp.add(12)
            }
        }
    }

    fun getNotePosition(staff: StaffLines, sym: ClassWithCoordinates) {

        if (staff.clef == "f") {

            if (sym.yPos < staff.staff0 + 1) {

                sym.noteSemantic = "C4"

            } else if (staff.staff0 + 1 <= sym.yPos && sym.yPos < staff.staff1 - 1) {

                sym.noteSemantic = "B3"

            } else if (staff.staff1 - 1 <= sym.yPos && sym.yPos < staff.staff1 + 1) {

                sym.noteSemantic = "A3"

            } else if (staff.staff1 + 1 <= sym.yPos && sym.yPos < staff.staff2 - 1) {

                sym.noteSemantic = "G3"

            } else if (staff.staff2 - 1 <= sym.yPos && sym.yPos < staff.staff2 + 1) {

                sym.noteSemantic = "F3"

            } else if (staff.staff2 + 1 <= sym.yPos && sym.yPos < staff.staff3 - 1) {

                sym.noteSemantic = "E3"

            } else if (staff.staff3 - 1 <= sym.yPos && sym.yPos < staff.staff3 + 1) {

                sym.noteSemantic = "D3"

            } else if (staff.staff3 + 1 <= sym.yPos && sym.yPos < staff.staff4 - 1) {

                sym.noteSemantic = "C3"

            } else if (staff.staff4 - 1 <= sym.yPos && sym.yPos < staff.staff4 + 1) {

                sym.noteSemantic = "B2"

            } else if (staff.staff4 + 1 <= sym.yPos && sym.yPos < staff.staff5 - 1) {

                sym.noteSemantic = "A2"

            } else if (staff.staff5 - 1 <= sym.yPos && sym.yPos < staff.staff5 + 1) {

                sym.noteSemantic = "G2"

            } else if (staff.staff5 + 1 <= sym.yPos && sym.yPos < staff.staff6 - 1) {

                sym.noteSemantic = "F2"

            } else if (staff.staff6 - 1 <= sym.yPos) {

                sym.noteSemantic = "E2"

            }

        } else {

            if (sym.yPos < staff.staff0 + 1) {

                sym.noteSemantic = "A5"

            } else if (staff.staff0 + 1 <= sym.yPos && sym.yPos < staff.staff1 - 1) {

                sym.noteSemantic = "G5"

            } else if (staff.staff1 - 1 <= sym.yPos && sym.yPos < staff.staff1 + 1) {

                sym.noteSemantic = "F5"

            } else if (staff.staff1 + 1 <= sym.yPos && sym.yPos < staff.staff2 - 1) {

                sym.noteSemantic = "E5"

            } else if (staff.staff2 - 1 <= sym.yPos && sym.yPos < staff.staff2 + 1) {

                sym.noteSemantic = "D5"

            } else if (staff.staff2 + 1 <= sym.yPos && sym.yPos < staff.staff3 - 1) {

                sym.noteSemantic = "C5"

            } else if (staff.staff3 - 1 <= sym.yPos && sym.yPos < staff.staff3 + 1) {

                sym.noteSemantic = "B4"

            } else if (staff.staff3 + 1 <= sym.yPos && sym.yPos < staff.staff4 - 1) {

                sym.noteSemantic = "A4"

            } else if (staff.staff4 - 1 <= sym.yPos && sym.yPos < staff.staff4 + 1) {

                sym.noteSemantic = "G4"

            } else if (staff.staff4 + 1 <= sym.yPos && sym.yPos < staff.staff5 - 1) {

                sym.noteSemantic = "F4"

            } else if (staff.staff5 - 1 <= sym.yPos && sym.yPos < staff.staff5 + 1) {

                sym.noteSemantic = "E4"

            } else if (staff.staff5 + 1 <= sym.yPos && sym.yPos < staff.staff6 - 1) {

                sym.noteSemantic = "D4"

            } else if (staff.staff6 - 1 <= sym.yPos) {

                sym.noteSemantic = "C4"

            }
        }

    }

    fun getSemantics() {

        for (doubleStaff in container3) {

            val gStaff = doubleStaff.staffGClef
            val fStaff = doubleStaff.staffFClef
            var staf = doubleStaff.staffBoth
            val gstart = gStaff!!.clefObject!!.xPos
            val fstart = fStaff!!.clefObject!!.xPos
            val fend = 610.0
            val gend = 610.0
            for (symbols in gStaff!!.symbols) {

                if (symbols.cName.contains("notehead", false)) {

                    if (symbols.xPos - gstart <= (gend - gstart) / 20) {
                        gStaff.notes1.add(symbols)
                        staf.notes1.add(symbols)
                    } else if (symbols.xPos - gstart >= 1 * (gend - gstart) / 20 && symbols.xPos - gstart <= 2 * (gend - gstart) / 20) {
                        gStaff.notes2.add(symbols)
                        staf.notes2.add(symbols)
                    } else if (symbols.xPos - gstart >= 2 * (gend - gstart) / 20 && symbols.xPos - gstart <= 3 * (gend - gstart) / 20) {
                        gStaff.notes3.add(symbols)
                        staf.notes3.add(symbols)
                    } else if (symbols.xPos - gstart >= 3 * (gend - gstart) / 20 && symbols.xPos - gstart <= 4 * (gend - gstart) / 20) {
                        gStaff.notes4.add(symbols)
                        staf.notes4.add(symbols)
                    } else if (symbols.xPos - gstart >= 4 * (gend - gstart) / 20 && symbols.xPos - gstart <= 5 * (gend - gstart) / 20) {
                        gStaff.notes5.add(symbols)
                        staf.notes5.add(symbols)
                    } else if (symbols.xPos - gstart >= 5 * (gend - gstart) / 20 && symbols.xPos - gstart <= 6 * (gend - gstart) / 20) {
                        gStaff.notes6.add(symbols)
                        staf.notes6.add(symbols)
                    } else if (symbols.xPos - gstart >= 6 * (gend - gstart) / 20 && symbols.xPos - gstart <= 7 * (gend - gstart) / 20) {
                        gStaff.notes7.add(symbols)
                        staf.notes7.add(symbols)
                    } else if (symbols.xPos - gstart >= 7 * (gend - gstart) / 20 && symbols.xPos - gstart <= 8 * (gend - gstart) / 20) {
                        gStaff.notes8.add(symbols)
                        staf.notes8.add(symbols)
                    } else if (symbols.xPos - gstart >= 8 * (gend - gstart) / 20 && symbols.xPos - gstart <= 9 * (gend - gstart) / 20) {
                        gStaff.notes9.add(symbols)
                        staf.notes9.add(symbols)
                    } else if (symbols.xPos - gstart >= 9 * (gend - gstart) / 20 && symbols.xPos - gstart <= 10 * (gend - gstart) / 20) {
                        gStaff.notes10.add(symbols)
                        staf.notes10.add(symbols)
                    } else if (symbols.xPos - gstart >= 10 * (gend - gstart) / 20 && symbols.xPos - gstart <= 11 * (gend - gstart) / 20) {
                        gStaff.notes11.add(symbols)
                        staf.notes11.add(symbols)
                    } else if (symbols.xPos - gstart >= 11 * (gend - gstart) / 20 && symbols.xPos - gstart <= 12 * (gend - gstart) / 20) {
                        gStaff.notes12.add(symbols)
                        staf.notes12.add(symbols)
                    } else if (symbols.xPos - gstart >= 12 * (gend - gstart) / 20 && symbols.xPos - gstart <= 13 * (gend - gstart) / 20) {
                        gStaff.notes13.add(symbols)
                        staf.notes13.add(symbols)
                    } else if (symbols.xPos - gstart >= 13 * (gend - gstart) / 20 && symbols.xPos - gstart <= 14 * (gend - gstart) / 20) {
                        gStaff.notes14.add(symbols)
                        staf.notes14.add(symbols)
                    } else if (symbols.xPos - gstart >= 14 * (gend - gstart) / 20 && symbols.xPos - gstart <= 15 * (gend - gstart) / 20) {
                        gStaff.notes15.add(symbols)
                        staf.notes15.add(symbols)
                    } else if (symbols.xPos - gstart >= 15 * (gend - gstart) / 20 && symbols.xPos - gstart <= 16 * (gend - gstart) / 20) {
                        gStaff.notes16.add(symbols)
                        staf.notes16.add(symbols)
                    } else if (symbols.xPos - gstart >= 16 * (gend - gstart) / 20 && symbols.xPos - gstart <= 17 * (gend - gstart) / 20) {
                        gStaff.notes17.add(symbols)
                        staf.notes17.add(symbols)
                    } else if (symbols.xPos - gstart >= 17 * (gend - gstart) / 20 && symbols.xPos - gstart <= 18 * (gend - gstart) / 20) {
                        gStaff.notes18.add(symbols)
                        staf.notes18.add(symbols)
                    } else if (symbols.xPos - gstart >= 18 * (gend - gstart) / 20 && symbols.xPos - gstart <= 19 * (gend - gstart) / 20) {
                        gStaff.notes19.add(symbols)
                        staf.notes19.add(symbols)
                    } else if (symbols.xPos - gstart >= 19 * (gend - gstart) / 20 && symbols.xPos - gstart <= 20 * (gend - gstart) / 20) {
                        gStaff.notes20.add(symbols)
                        staf.notes20.add(symbols)
                    }

                } else {

                }

            }
            for (symbols in fStaff!!.symbols) {

                if (symbols.cName.contains("notehead", false)) {

                    if (symbols.xPos - fstart <= (fend - fstart) / 20) {
                        fStaff.notes1.add(symbols)
                        staf.notes1.add(symbols)
                    } else if (symbols.xPos - fstart >= 1 * (fend - fstart) / 20 && symbols.xPos - fstart <= 2 * (fend - fstart) / 20) {
                        fStaff.notes2.add(symbols)
                        staf.notes2.add(symbols)
                    } else if (symbols.xPos - fstart >= 2 * (fend - fstart) / 20 && symbols.xPos - fstart <= 3 * (fend - fstart) / 20) {
                        fStaff.notes3.add(symbols)
                        staf.notes3.add(symbols)
                    } else if (symbols.xPos - fstart >= 3 * (fend - fstart) / 20 && symbols.xPos - fstart <= 4 * (fend - fstart) / 20) {
                        fStaff.notes4.add(symbols)
                        staf.notes4.add(symbols)
                    } else if (symbols.xPos - fstart >= 4 * (fend - fstart) / 20 && symbols.xPos - fstart <= 5 * (fend - fstart) / 20) {
                        fStaff.notes5.add(symbols)
                        staf.notes5.add(symbols)
                    } else if (symbols.xPos - fstart >= 5 * (fend - fstart) / 20 && symbols.xPos - fstart <= 6 * (fend - fstart) / 20) {
                        fStaff.notes6.add(symbols)
                        staf.notes6.add(symbols)
                    } else if (symbols.xPos - fstart >= 6 * (fend - fstart) / 20 && symbols.xPos - fstart <= 7 * (fend - fstart) / 20) {
                        fStaff.notes7.add(symbols)
                        staf.notes7.add(symbols)
                    } else if (symbols.xPos - fstart >= 7 * (fend - fstart) / 20 && symbols.xPos - fstart <= 8 * (fend - fstart) / 20) {
                        fStaff.notes8.add(symbols)
                        staf.notes8.add(symbols)
                    } else if (symbols.xPos - fstart >= 8 * (fend - fstart) / 20 && symbols.xPos - fstart <= 9 * (fend - fstart) / 20) {
                        fStaff.notes9.add(symbols)
                        staf.notes9.add(symbols)
                    } else if (symbols.xPos - fstart >= 9 * (fend - fstart) / 20 && symbols.xPos - fstart <= 10 * (fend - fstart) / 20) {
                        fStaff.notes10.add(symbols)
                        staf.notes10.add(symbols)
                    } else if (symbols.xPos - fstart >= 10 * (fend - fstart) / 20 && symbols.xPos - fstart <= 11 * (fend - fstart) / 20) {
                        fStaff.notes11.add(symbols)
                        staf.notes11.add(symbols)
                    } else if (symbols.xPos - fstart >= 11 * (fend - fstart) / 20 && symbols.xPos - fstart <= 12 * (fend - fstart) / 20) {
                        fStaff.notes12.add(symbols)
                        staf.notes12.add(symbols)
                    } else if (symbols.xPos - fstart >= 12 * (fend - fstart) / 20 && symbols.xPos - fstart <= 13 * (fend - fstart) / 20) {
                        fStaff.notes13.add(symbols)
                        staf.notes13.add(symbols)
                    } else if (symbols.xPos - fstart >= 13 * (fend - fstart) / 20 && symbols.xPos - fstart <= 14 * (fend - fstart) / 20) {
                        fStaff.notes14.add(symbols)
                        staf.notes14.add(symbols)
                    } else if (symbols.xPos - fstart >= 14 * (fend - fstart) / 20 && symbols.xPos - fstart <= 15 * (fend - fstart) / 20) {
                        fStaff.notes15.add(symbols)
                        staf.notes15.add(symbols)
                    } else if (symbols.xPos - fstart >= 15 * (fend - fstart) / 20 && symbols.xPos - fstart <= 16 * (fend - fstart) / 20) {
                        fStaff.notes16.add(symbols)
                        staf.notes16.add(symbols)
                    } else if (symbols.xPos - fstart >= 16 * (fend - fstart) / 20 && symbols.xPos - fstart <= 17 * (fend - fstart) / 20) {
                        fStaff.notes17.add(symbols)
                        staf.notes17.add(symbols)
                    } else if (symbols.xPos - fstart >= 17 * (fend - fstart) / 20 && symbols.xPos - fstart <= 18 * (fend - fstart) / 20) {
                        fStaff.notes18.add(symbols)
                        staf.notes18.add(symbols)
                    } else if (symbols.xPos - fstart >= 18 * (fend - fstart) / 20 && symbols.xPos - fstart <= 19 * (fend - fstart) / 20) {
                        fStaff.notes19.add(symbols)
                        staf.notes19.add(symbols)
                    } else if (symbols.xPos - fstart >= 19 * (fend - fstart) / 20 && symbols.xPos - fstart <= 20 * (fend - fstart) / 20) {
                        fStaff.notes20.add(symbols)
                        staf.notes20.add(symbols)
                    }

                } else {

                }

            }
        }
    }

    private object HOLDER2 {
        val INSTANCE = SemanticsConverter()
    }

    companion object {
        val instance: SemanticsConverter by lazy { HOLDER2.INSTANCE }
    }

}