package com.glella.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CrimeListFragment : Fragment() {


    lateinit var mCrimesRecyclerView: RecyclerView
    private var mAdapter: CrimeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        mCrimesRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        mCrimesRecyclerView.setLayoutManager(LinearLayoutManager(activity))

        updateUI()

        return view
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val crimes = CrimeLab.mCrimes

        if (mAdapter == null) {
            mAdapter = CrimeAdapter(crimes)
            mCrimesRecyclerView.setAdapter(mAdapter)
        } else {
            mAdapter!!.notifyDataSetChanged()
        }


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

    private inner class CrimeAdapter(private val mCrimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {


        override fun getItemCount(): Int {
            return mCrimes.size
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