package com.glella.criminalintent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

import static android.widget.CompoundButton.OnCheckedChangeListener;
import static android.widget.CompoundButton.OnClickListener;

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;
    private Button mReportButton;
    private Button mSuspectButton;
    // Challenge
    private Button mCallSuspectButton;
    private static final String[] CONTACTS_PERMISSIONS = new String[]{
            Manifest.permission.READ_CONTACTS
    };
    private static final int REQUEST_CONTACTS_PERMISSIONS = 3;

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "dialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;

    public static CrimeFragment newInstance(UUID crimeID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeID);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UUID crimeID = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        UUID crimeID = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Nothing to do here
            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        final PackageManager packageManager = getActivity().getPackageManager();

        mReportButton = (Button) v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Challenge - Manual Method
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, );
                startActivity(i);
                */
                // Challenge - ShareCompat IntentBuilder
                String mimeType = "text/plain";
                String title = getString(R.string.send_report);
                Intent i = ShareCompat.IntentBuilder.from(getActivity())
                        .setChooserTitle(title)
                        .setType(mimeType)
                        .setText(getCrimeReport())
                        .createChooserIntent();
                        //.getIntent();

                if (i.resolveActivity(packageManager) != null){
                    startActivity(i);
                }
            }
        });

        // Challenge
        final Intent callContact = new Intent(Intent.ACTION_DIAL);
        mCallSuspectButton = (Button) v.findViewById(R.id.call_suspect);
        mCallSuspectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Uri number = Uri.parse(“tel:” + mCrime.getPhone()); // the “tel:” is needed to start activity
                String numberString = "tel:" + mCrime.getSuspectPhone();
                Uri number = Uri.parse(numberString);
                callContact.setData(number);
                startActivity(callContact);
            }
        });


        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton = (Button) v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCallSuspectButton.setEnabled(true);
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });



        if (mCrime.getSuspect() != null) {
            mSuspectButton.setText(mCrime.getSuspect());
            mCallSuspectButton.setEnabled(true);
        } else {
            mCallSuspectButton.setEnabled(false);
        }

        //PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact,
                PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mSuspectButton.setEnabled(false);
        }

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            // Get the suspect's name - Simplified to method
            String suspectName = getSuspectName(data);
            mCrime.setSuspect(suspectName);
            mSuspectButton.setText(suspectName);

            // Get the suspect's mobile phone (cell phone) number.
            if (hasContactPermission()) {
                updateSuspectPhone();
            } else {
                // This will call onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults).
                requestPermissions(CONTACTS_PERMISSIONS, REQUEST_CONTACTS_PERMISSIONS);
            }
        }
    }

    // Challenge
    private String getSuspectName(Intent data) {
        Uri contactUri = data.getData();
        // Specify which fields you want your query to return values for
        String[] queryFields = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        // Perform your query - the contactUri is like a "where" clause here.
        Cursor c = getActivity().getContentResolver()
                .query(contactUri, queryFields, null, null, null);
        try {
            // Double-check that you actually got results.
            if (c.getCount() == 0) {
                return null;
            }
            // Pull out the first column of the first row of data - that is your suspect's name.
            c.moveToFirst();

            mCrime.setSuspectID(c.getString(0));
            String suspectName = c.getString(1);
            return suspectName;

        } finally {
            c.close();
        }
    }

    // Challenge
    private String getSuspectPhoneNumber(String contactId) {
        String suspectPhoneNumber = null;
        // The content URI of the CommonDataKinds.Phone
        Uri phoneContactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // The columns to return for each row
        String[] queryFields = new String[] {
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,   // which is the default phone number.
                ContactsContract.CommonDataKinds.Phone.TYPE,
        };
        // Selection criteria
        String mSelectionClause = ContactsContract.Data.CONTACT_ID + " = ?";
        // Selection criteria
        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = contactId;
        // Does a query against the table and returns a Cursor object
        Cursor c = getActivity().getContentResolver()
                .query(phoneContactUri,queryFields, mSelectionClause, mSelectionArgs, null );
        try {
            // Double-check that you actually got results.
            if (c.getCount() == 0) {
                return null;
            }
            while (c.moveToNext()) {
                int phoneType = c.getInt(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                    suspectPhoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                    break;
                }
            }
        } finally {
            c.close();
        }
        return suspectPhoneNumber;
    }

    private boolean hasContactPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), CONTACTS_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CONTACTS_PERMISSIONS:
                if (hasContactPermission()) {
                    updateSuspectPhone();
                }
        }
    }

    private void updateSuspectPhone () {
        String suspectPhoneNumber = getSuspectPhoneNumber(mCrime.getSuspectID());
        mCrime.setSuspectPhone(suspectPhoneNumber);
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report,
                mCrime.getTitle(), dateString, solvedString, suspect);

        return report;
    }


}
