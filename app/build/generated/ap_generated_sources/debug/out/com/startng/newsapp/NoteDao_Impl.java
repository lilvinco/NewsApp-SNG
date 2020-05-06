package com.startng.newsapp;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfNote;

  public NoteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `notes`(`id`,`note_body`,`body`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
        if (value.getHeading() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getHeading());
        }
        if (value.getBody() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBody());
        }
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `notes` SET `id` = ?,`note_body` = ?,`body` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
        if (value.getHeading() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getHeading());
        }
        if (value.getBody() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBody());
        }
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public long insertNote(Note note) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfNote.insertAndReturnId(note);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNote(Note note) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNote(Note... note) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfNote.handleMultiple(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateNote(Note repos) {
    __db.beginTransaction();
    try {
      __updateAdapterOfNote.handle(repos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Note> getNotes() {
    final String _sql = "SELECT * FROM notes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfHeading = _cursor.getColumnIndexOrThrow("note_body");
      final int _cursorIndexOfBody = _cursor.getColumnIndexOrThrow("body");
      final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Note _item;
        final String _tmpHeading;
        _tmpHeading = _cursor.getString(_cursorIndexOfHeading);
        final String _tmpBody;
        _tmpBody = _cursor.getString(_cursorIndexOfBody);
        _item = new Note(_tmpHeading,_tmpBody);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
