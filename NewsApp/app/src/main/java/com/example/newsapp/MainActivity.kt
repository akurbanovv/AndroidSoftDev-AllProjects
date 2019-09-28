package com.example.newsapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity(), MenuFragment.OnFragmentInteractionListener {
    override fun onButtonClicked(news: News) {
        val newsFragment = NewsFragment.newInstance(news)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, newsFragment, "newsFrag")
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val newsFragment = NewsFragment.newInstance(News.departmentNews[0])
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, newsFragment, "newsFrag")
                .commit()
        } else {
            findViewById<ViewPager>(R.id.news_viewPager).adapter =
                ViewPagerAdapter(supportFragmentManager)
        }
    }
}
