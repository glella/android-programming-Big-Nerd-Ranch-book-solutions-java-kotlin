package com.glella.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView

class CrimeListFragment : Fragment() {

    lateinit var mCrimesRecyclerView: RecyclerView
    private var mAdapter: CrimeAdapter? = null
    var mSubtitleVisible : Boolean = false
    private val SAVED_SUBTITLE_VISIBLE = "subtitle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        mCrimesRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        mCrimesRecyclerView.setLayoutManager(LinearLayoutManager(activity))

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        }

        updateUI()

        return view
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        //val crimes = CrimeLab.mCrimes
        val crimes = CrimeLab.getCrimes()
        CrimeLab.mCrimes = crimes // Make it sync with database that gets loaded first

        if (mAdapter == null) {
            mAdapter = CrimeAdapter(crimes)
            mCrimesRecyclerView.setAdapter(mAdapter)
        } else {
            mAdapter!!.setCrimes(crimes)
            mAdapter!!.notifyDataSetChanged()
        }

        updateSubtitle()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu!!.findItem(R.id.show_subtitle)
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem.setTitle(R.string.show_subtitle)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {

            R.id.new_crime -> {
                val crime = Crime()
                CrimeLab.addCrime(crime)
                val intent = CrimePagerActivity.newIntent(activity!!, crime.mId)
                startActivity(intent)
                return true
            }

            R.id.show_subtitle -> {
                mSubtitleVisible = !mSubtitleVisible
                activity!!.invalidateOptionsMenu()
                updateSubtitle()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateSubtitle() {
        //val crimeCount = CrimeLab.mCrimes.size
        val crimeCount = CrimeLab.getCrimes().size
        var subtitle: String? = getString(R.string.subtitle_format, crimeCount)

        if (!mSubtitleVisible) {
            subtitle = null
        }

        val activity = activity as AppCompatActivity?
        activity!!.supportActionBar!!.setSubtitle(subtitle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible)
    }

    private inner class CrimeHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false)), View.OnClickListener {

        private val mTitleTextView: TextView
        private val mDateTextView: TextView
        private var mCrime: Crime? = null
        private val mSolvedImageView: ImageView

        init {
            itemView.setOnClickListener(this)

            mTitleTextView = itemView.findViewById(R.id.crime_title)
            mDateTextView = itemView.findViewById(R.id.crime_date)
            mSolvedImageView = itemView.findViewById(R.id.crime_solved)
        }

        fun bind(crime: Crime) {
            mCrime = crime
            mTitleTextView.setText(mCrime!!.mTitle)
            mDateTextView.setText(mCrime!!.mDate.toString())

            mSolvedImageView.visibility = if (crime.mSolved) View.VISIBLE else View.GONE
        }

        override fun onClick(view: View) {
            //Toast.makeText(activity, mCrime!!.mTitle + " clicked!", Toast.LENGTH_SHORT).show()
            val intent = CrimePagerActivity.newIntent(activity!!, mCrime!!.mId)
            startActivity(intent)
        }
    }

    private inner class CrimeAdapter(private var mCrimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {


        override fun getItemCount(): Int {
            return mCrimes.size
        }

        fun setCrimes(crimes : List<Crime>) {
            mCrimes = crimes
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CrimeHolder {

            val layoutInflater = LayoutInflater.from(activity)
            return CrimeHolder(layoutInflater, viewGroup)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = mCrimes[position]
            holder.bind(crime)
        }
    }

}