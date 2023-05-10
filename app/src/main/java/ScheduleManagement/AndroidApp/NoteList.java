package ScheduleManagement.AndroidApp;

import java.io.Serializable;
import java.util.ArrayList;

public class NoteList implements Serializable {
    private static final long serialVersionUID = 435616461097017241L;
    private ArrayList<Note> _noteList;

    public NoteList(){
        _noteList  = new ArrayList<>();
    }

    public NoteList(ArrayList<Note> list){
        if(list != null){
            this._noteList = list;
        }
    }

    private void SetAllId()
    {
        int counterId = 0;
        for (Note note: this._noteList){
            if(note.getId() <= 0){
                note.setId(this.GetLastId() + 1);
            }
        }
    }

    private int GetLastId(){
        int maxId = 0;
        for (Note note: this._noteList){
            if(maxId < note.getId()){
                maxId = note.getId();
            }
        }
        return maxId;
    }

    public void addNote(Note newNote){
        if(newNote != null){
            newNote.setId(this.GetLastId());
            this._noteList.add(newNote);
        }
    }

    public Note getNoteById(int id){
        for(Note note: this._noteList){
            if(note.getId() == id){
                return note;
            }
        }
        return null;
    }

    public Note getNoteByIndex(int index){
        return this._noteList.get(index);
    }

    public ArrayList<Note> getNoteList(){
        return this._noteList;
    }
}
