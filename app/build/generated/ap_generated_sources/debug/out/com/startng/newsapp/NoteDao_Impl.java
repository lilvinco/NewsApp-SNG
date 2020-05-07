package com.startng.newsapp;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
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

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public NoteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `notes` (`noteId`,`noteTitle`,`noteContent`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getNoteId());
        if (value.getNoteTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteTitle());
        }
        if (value.getNoteContent() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNoteContent());
        }
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes` WHERE `noteId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getNoteId());
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `notes` SET `noteId` = ?,`noteTitle` = ?,`noteContent` = ? WHERE `noteId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getNoteId());
        if (value.getNoteTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteTitle());
        }
        if (value.getNoteContent() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNoteContent());
        }
        stmt.bindLong(4, value.getNoteId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM notes";
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
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Note>> getAllNotes() {
    final String _sql = "SELECT * FROM notes ORDER BY noteId DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"notes"}, false, new Callable<List<Note>>() {
      @Override
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "noteId");
          final int _cursorIndexOfNoteTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "noteTitle");
          final int _cursorIndexOfNoteContent = CursorUtil.getColumnIndexOrThrow(_cursor, "noteContent");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Note _item;
            final int _tmpNoteId;
            _tmpNoteId = _cursor.getInt(_cursorIndexOfNoteId);
            final String _tmpNoteTitle;
            _tmpNoteTitle = _cursor.getString(_cursorIndexOfNoteTitle);
            final String _tmpNoteContent;
            _tmpNoteContent = _cursor.getString(_cursorIndexOfNoteContent);
            _item = new Note(_tmpNoteId,_tmpNoteTitle,_tmpNoteContent);
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
  public LiveData<Note> getNote(final int noteId) {
    final String _sql = "SELECT * FROM notes WHERE noteId=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noteId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"notes"}, false, new Callable<Note>() {
      @Override
      public Note call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "noteId");
          final int _cursorIndexOfNoteTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "noteTitle");
          final int _cursorIndexOfNoteContent = CursorUtil.getColumnIndexOrThrow(_cursor, "noteContent");
          final Note _result;
          if(_cursor.moveToFirst()) {
            final int _tmpNoteId;
            _tmpNoteId = _cursor.getInt(_cursorIndexOfNoteId);
            final String _tmpNoteTitle;
            _tmpNoteTitle = _cursor.getString(_cursorIndexOfNoteTitle);
            final String _tmpNoteContent;
            _tmpNoteContent = _cursor.getString(_cursorIndexOfNoteContent);
            _result = new Note(_tmpNoteId,_tmpNoteTitle,_tmpNoteContent);
          } else {
            _result = null;
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
