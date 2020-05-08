package com.startng.newsapp.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.startng.newsapp.HeadlinesActivity;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HeadlinesActivity> __insertionAdapterOfHeadlinesActivity;

  private final EntityDeletionOrUpdateAdapter<HeadlinesActivity> __deletionAdapterOfHeadlinesActivity;

  private final EntityDeletionOrUpdateAdapter<HeadlinesActivity> __updateAdapterOfHeadlinesActivity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNoteById;

  public NoteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHeadlinesActivity = new EntityInsertionAdapter<HeadlinesActivity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `notes` (`id`,`text`,`date`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HeadlinesActivity value) {
        stmt.bindLong(1, value.getId());
        if (value.getNoteText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteText());
        }
        stmt.bindLong(3, value.getNoteDate());
      }
    };
    this.__deletionAdapterOfHeadlinesActivity = new EntityDeletionOrUpdateAdapter<HeadlinesActivity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HeadlinesActivity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfHeadlinesActivity = new EntityDeletionOrUpdateAdapter<HeadlinesActivity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `notes` SET `id` = ?,`text` = ?,`date` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HeadlinesActivity value) {
        stmt.bindLong(1, value.getId());
        if (value.getNoteText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteText());
        }
        stmt.bindLong(3, value.getNoteDate());
        stmt.bindLong(4, value.getId());
      }
    };
    this.__preparedStmtOfDeleteNoteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM notes WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertNote(final HeadlinesActivity note) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfHeadlinesActivity.insert(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNote(final HeadlinesActivity... note) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfHeadlinesActivity.handleMultiple(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateNote(final HeadlinesActivity note) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfHeadlinesActivity.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNoteById(final int noteId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNoteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, noteId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteNoteById.release(_stmt);
    }
  }

  @Override
  public List<HeadlinesActivity> getNotes() {
    final String _sql = "SELECT * FROM notes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfNoteDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final List<HeadlinesActivity> _result = new ArrayList<HeadlinesActivity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final HeadlinesActivity _item;
        final String _tmpNoteText;
        _tmpNoteText = _cursor.getString(_cursorIndexOfNoteText);
        final long _tmpNoteDate;
        _tmpNoteDate = _cursor.getLong(_cursorIndexOfNoteDate);
        _item = new HeadlinesActivity(_tmpNoteText,_tmpNoteDate);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public HeadlinesActivity getNoteById(final int noteId) {
    final String _sql = "SELECT * FROM notes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noteId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfNoteDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final HeadlinesActivity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpNoteText;
        _tmpNoteText = _cursor.getString(_cursorIndexOfNoteText);
        final long _tmpNoteDate;
        _tmpNoteDate = _cursor.getLong(_cursorIndexOfNoteDate);
        _result = new HeadlinesActivity(_tmpNoteText,_tmpNoteDate);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
