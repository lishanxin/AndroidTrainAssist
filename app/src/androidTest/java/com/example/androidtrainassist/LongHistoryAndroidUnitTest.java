package com.example.androidtrainassist;

import android.os.Parcel;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by sx on 2018/10/12.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LongHistoryAndroidUnitTest {

    public static final String TEST_STRING = "This is a string";
    public static final long TEST_LONG = 12345678L;
    private LogHistory mLogHistory;

    @Before
    public void createLogHistory(){
        mLogHistory = new LogHistory();
    }

    @Test
    public void logHistory_ParcelableWriteRead(){
        // Set up the Parcelable object to send and receive
        mLogHistory.addEntry(TEST_STRING, TEST_LONG);
        mLogHistory.addEntry("one", (long)1234444);

        // Write the data
        Parcel parcel = Parcel.obtain();
        mLogHistory.writeToParcel(parcel, mLogHistory.describeContents());

        // After you are done with writing, you need to reset the parcel for reding
        parcel.setDataPosition(0);

        // Read the data
        LogHistory createdFromParcel = LogHistory.CREATOR.createFromParcel(parcel);
        List<Pair<String, Long>> createFromParcelData = createdFromParcel.getData();

        // Verify that the received data is correct
        assertThat(createFromParcelData.size(), is(2));
        assertThat(createFromParcelData.get(0).first, is(TEST_STRING));
        assertThat(createFromParcelData.get(0).second, is(TEST_LONG));
    }
}
