package com.startng.newsapp.Database.DataAccessObject;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.startng.newsapp.Database.Model.Note;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Note> __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter<Note> __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter<Note> __updateAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllNotes;

  public NoteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `note_table` (`nid`,`note_title`,`note_text`,`time_stamp`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getNid());
        if (value.getNote_Title() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNote_Title());
        }
        if (value.getNote_Text() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNote_Text());
        }
        if (value.getTime_Stamp() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTime_Stamp());
        }
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `note_table` WHERE `nid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getNid());
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `note_table` SET `nid` = ?,`note_title` = ?,`note_text` = ?,`time_stamp` = ? WHERE `nid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getNid());
        if (value.getNote_Title() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNote_Title());
        }
        if (value.getNote_Text() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNote_Text());
        }
        if (value.getTime_Stamp() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTime_Stamp());
        }
        stmt.bindLong(5, value.getNid());
      }
    };
    this.__preparedStmtOfDeleteAllNotes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM note_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Note note) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNote.insert(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Note note) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Note note) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllNotes() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllNotes.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllNotes.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Note>> getAllNotes() {
    final String _sql = "SELECT * FROM note_table ORDER BY time_stamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"note_table"}, false, new Callable<List<Note>>() {
      @Override
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNid = CursorUtil.getColumnIndexOrThrow(_cursor, "nid");
          final int _cursorIndexOfNoteTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "note_title");
          final int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "note_text");
          final int _cursorIndexOfTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "time_stamp");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Note _item;
            final String _tmpNote_Title;
            _tmpNote_Title = _cursor.getString(_cursorIndexOfNoteTitle);
            final String _tmpNote_Text;
            _tmpNote_Text = _cursor.getString(_cursorIndexOfNoteText);
            final String _tmpTime_Stamp;
            _tmpTime_Stamp = _cursor.getString(_cursorIndexOfTimeStamp);
            _item = new Note(_tmpNote_Title,_tmpNote_Text,_tmpTime_Stamp);
            final int _tmpNid;
            _tmpNid = _cursor.getInt(_cursorIndexOfNid);
            _item.setNid(_tmpNid);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Note>> loadAllByIds(final int[] noteIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT ");
    _stringBuilder.append("*");
    _stringBuilder.append(" FROM note_table WHERE nid IN (");
    final int _inputSize = noteIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : noteIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"note_table"}, false, new Callable<List<Note>>() {
      @Override
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNid = CursorUtil.getColumnIndexOrThrow(_cursor, "nid");
          final int _cursorIndexOfNoteTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "note_title");
          final int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "note_text");
          final int _cursorIndexOfTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "time_stamp");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Note _item_1;
            final String _tmpNote_Title;
            _tmpNote_Title = _cursor.getString(_cursorIndexOfNoteTitle);
            final String _tmpNote_Text;
            _tmpNote_Text = _cursor.getString(_cursorIndexOfNoteText);
            final String _tmpTime_Stamp;
            _tmpTime_Stamp = _cursor.getString(_cursorIndexOfTimeStamp);
            _item_1 = new Note(_tmpNote_Title,_tmpNote_Text,_tmpTime_Stamp);
            final int _tmpNid;
            _tmpNid = _cursor.getInt(_cursorIndexOfNid);
            _item_1.setNid(_tmpNid);
            _result.add(_item_1);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Note>> findByTitle(final String title) {
    final String _sql = "SELECT * FROM note_table WHERE note_title LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"note_table"}, false, new Callable<List<Note>>() {
      @Override
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNid = CursorUtil.getColumnIndexOrThrow(_cursor, "nid");
          final int _cursorIndexOfNoteTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "note_title");
          final int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "note_text");
          final int _cursorIndexOfTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "time_stamp");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Note _item;
            final String _tmpNote_Title;
            _tmpNote_Title = _cursor.getString(_cursorIndexOfNoteTitle);
            final String _tmpNote_Text;
            _tmpNote_Text = _cursor.getString(_cursorIndexOfNoteText);
            final String _tmpTime_Stamp;
            _tmpTime_Stamp = _cursor.getString(_cursorIndexOfTimeStamp);
            _item = new Note(_tmpNote_Title,_tmpNote_Text,_tmpTime_Stamp);
            final int _tmpNid;
            _tmpNid = _cursor.getInt(_cursorIndexOfNid);
            _item.setNid(_tmpNid);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
