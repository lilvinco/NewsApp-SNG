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
public final class MyNoteDao_Impl implements MyNoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NoteBook> __insertionAdapterOfNoteBook;

  private final EntityDeletionOrUpdateAdapter<NoteBook> __deletionAdapterOfNoteBook;

  private final EntityDeletionOrUpdateAdapter<NoteBook> __updateAdapterOfNoteBook;

  private final SharedSQLiteStatement __preparedStmtOfClearTable;

  public MyNoteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNoteBook = new EntityInsertionAdapter<NoteBook>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `table_1` (`NoteNo`,`Note_Title`,`Note_Content`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteBook value) {
        stmt.bindLong(1, value.getNoteNo());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getContent() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getContent());
        }
      }
    };
    this.__deletionAdapterOfNoteBook = new EntityDeletionOrUpdateAdapter<NoteBook>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `table_1` WHERE `NoteNo` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteBook value) {
        stmt.bindLong(1, value.getNoteNo());
      }
    };
    this.__updateAdapterOfNoteBook = new EntityDeletionOrUpdateAdapter<NoteBook>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `table_1` SET `NoteNo` = ?,`Note_Title` = ?,`Note_Content` = ? WHERE `NoteNo` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteBook value) {
        stmt.bindLong(1, value.getNoteNo());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getContent() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getContent());
        }
        stmt.bindLong(4, value.getNoteNo());
      }
    };
    this.__preparedStmtOfClearTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from table_1";
        return _query;
      }
    };
  }

  @Override
  public void insert(final NoteBook noteBook) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNoteBook.insert(noteBook);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final NoteBook noteBook) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfNoteBook.handle(noteBook);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete2(final NoteBook... noteBook) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfNoteBook.handleMultiple(noteBook);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final NoteBook noteBook) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfNoteBook.handle(noteBook);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearTable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearTable.release(_stmt);
    }
  }

  @Override
  public LiveData<List<NoteBook>> getAllNotes() {
    final String _sql = "SELECT * FROM table_1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"table_1"}, false, new Callable<List<NoteBook>>() {
      @Override
      public List<NoteBook> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoteNo = CursorUtil.getColumnIndexOrThrow(_cursor, "NoteNo");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Note_Title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "Note_Content");
          final List<NoteBook> _result = new ArrayList<NoteBook>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NoteBook _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            _item = new NoteBook(_tmpTitle,_tmpContent);
            final int _tmpNoteNo;
            _tmpNoteNo = _cursor.getInt(_cursorIndexOfNoteNo);
            _item.setNoteNo(_tmpNoteNo);
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
