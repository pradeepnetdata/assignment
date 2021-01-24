package com.barclays.stock;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
  /*  DatabaseHelper mMockDatabaseHelper;
    PreferencesHelper mMockPreferencesHelper;*/
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

  /*  @Before
    public void setUp() {
        mDataManager = new DataManager(mMockRibotsService, mMockPreferencesHelper,
                mMockDatabaseHelper);
    }

    @Test
    public void setStocks() {
        Stocks ribot1 = TestDataFactory.makeRibot("r1");
        Stocks ribot2 = TestDataFactory.makeRibot("r2");
        List<Stocks> ribots = Arrays.asList(ribot1, ribot2);

        TestSubscriber<Stocks> result = new TestSubscriber<>();
        mDatabaseHelper.setStocks(ribots).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(ribots);

        Cursor cursor = mDatabaseHelper.getBriteDb()
                .query("SELECT * FROM " + Db.RibotProfileTable.TABLE_NAME);
        assertEquals(2, cursor.getCount());
        for (Stocks ribot : ribots) {
            cursor.moveToNext();
            assertEquals(ribot.profile(), Db.RibotProfileTable.parseCursor(cursor));
        }
    }

    @Test
    public void getStocks() {
        Stocks ribot1 = TestDataFactory.makeRibot("r1");
        Stocks ribot2 = TestDataFactory.makeRibot("r2");
        List<Stocks> ribots = Arrays.asList(ribot1, ribot2);

        mDatabaseHelper.setStocks(ribots).subscribe();

        TestSubscriber<List<Stocks>> result = new TestSubscriber<>();
        mDatabaseHelper.getStocks().subscribe(result);
        result.assertNoErrors();
        result.assertValue(ribots);
    }*/
}