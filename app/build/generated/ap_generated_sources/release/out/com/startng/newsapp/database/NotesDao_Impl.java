package com.startng.newsapp.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
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
public final class NotesDao_Impl implements NotesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Notes> __insertionAdapterOfNotes;

  private final EntityDeletionOrUpdateAdapter<Notes> __deletionAdapterOfNotes;

  private final EntityDeletionOrUpdateAdapter<Notes> __updateAdapterOfNotes;

  public NotesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNotes = new EntityInsertionAdapter<Notes>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `notes_table` (`id`,`noteTime`,`noteDesc`,`noteColor`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Notes value) {
        stmt.bindLong(1, value.getId());
        if (value.getNoteTime() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteTime());
        }
        if (value.getNoteDesc() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNoteDesc());
        }
        stmt.bindLong(4, value.getNoteColor());
      }
    };
    this.__deletionAdapterOfNotes = new EntityDeletionOrUpdateAdapter<Notes>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Notes value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfNotes = new EntityDeletionOrUpdateAdapter<Notes>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `notes_table` SET `id` = ?,`noteTime` = ?,`noteDesc` = ?,`noteColor` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Notes value) {
        stmt.bindLong(1, value.getId());
        if (value.getNoteTime() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteTime());
        }
        if (value.getNoteDesc() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNoteDesc());
        }
        stmt.bindLong(4, value.getNoteColor());
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public void insert(final Notes notes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNotes.insert(notes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNote(final Notes notes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfNotes.handle(notes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateNote(final Notes notes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfNotes.handle(notes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Notes>> getOrderedNotes() {
    final String _sql = "SELECT * FROM notes_table ORDER BY noteTime DESC ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"notes_table"}, false, new Callable<List<Notes>>() {
      @Override
      public List<Notes> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNoteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "noteTime");
          final int _cursorIndexOfNoteDesc = CursorUtil.getColumnIndexOrThrow(_cursor, "noteDesc");
          final int _cursorIndexOfNoteColor = CursorUtil.getColumnIndexOrThrow(_cursor, "noteColor");
          final List<Notes> _result = new ArrayList<Notes>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Notes _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNoteTime;
            _tmpNoteTime = _cursor.getString(_cursorIndexOfNoteTime);
            final String _tmpNoteDesc;
            _tmpNoteDesc = _cursor.getString(_cursorIndexOfNoteDesc);
            final int _tmpNoteColor;
            _tmpNoteColor = _cursor.getInt(_cursorIndexOfNoteColor);
            _item = new Notes(_tmpId,_tmpNoteTime,_tmpNoteDesc,_tmpNoteColor);
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
  public LiveData<Notes> getSingleNote(final int id) {
    final String _sql = "SELECT * FROM notes_table WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[]{"notes_table"}, false, new Callable<Notes>() {
      @Override
      public Notes call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNoteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "noteTime");
          final int _cursorIndexOfNoteDesc = CursorUtil.getColumnIndexOrThrow(_cursor, "noteDesc");
          final int _cursorIndexOfNoteColor = CursorUtil.getColumnIndexOrThrow(_cursor, "noteColor");
          final Notes _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNoteTime;
            _tmpNoteTime = _cursor.getString(_cursorIndexOfNoteTime);
            final String _tmpNoteDesc;
            _tmpNoteDesc = _cursor.getString(_cursorIndexOfNoteDesc);
            final int _tmpNoteColor;
            _tmpNoteColor = _cursor.getInt(_cursorIndexOfNoteColor);
            _result = new Notes(_tmpId,_tmpNoteTime,_tmpNoteDesc,_tmpNoteColor);
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
