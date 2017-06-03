package edu.tjrac.swant.bestcase.moudle.main

//package edu.tjrac.swant.bestcase.moudle;//package edu.tjrac.swant.bestcase;

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import butterknife.bindView
import de.hdodenhof.circleimageview.CircleImageView
import edu.tjrac.swant.bestcase.R
import edu.tjrac.swant.bestcase.base.BaseActivity
import edu.tjrac.swant.bestcase.common.adapter.ViewPagerAdapter
import edu.tjrac.swant.bestcase.manager.backgroundcreater.RandomBgCreater
import edu.tjrac.swant.bestcase.manager.db.DBConsts
import edu.tjrac.swant.bestcase.manager.db.DBHelper
import edu.tjrac.swant.bestcase.moudle.MainSeachActivity
import edu.tjrac.swant.bestcase.moudle.camera.Camera2Activity
import edu.tjrac.swant.bestcase.moudle.laboratory.HorizontalScrollViewTestActivity
import edu.tjrac.swant.bestcase.moudle.laboratory.diy_views.DiyViewsActivity
import edu.tjrac.swant.bestcase.moudle.laboratory.sensor.SensorActivity
import edu.tjrac.swant.bestcase.moudle.line.LinesActivity
import edu.tjrac.swant.bestcase.moudle.main.contacts.ContactsFragment
import edu.tjrac.swant.bestcase.moudle.main.gank.GankActivity
import edu.tjrac.swant.bestcase.moudle.main.job.JobFragment
import edu.tjrac.swant.bestcase.moudle.main.playground.DailyFragment
import edu.tjrac.swant.bestcase.moudle.settings.SettingsActivity
import edu.tjrac.swant.bestcase.moudle.user_center.UserCenterActivity
import java.util.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    val mToolbar: Toolbar by bindView(R.id.toolbar)
    val mFab: FloatingActionButton by bindView(R.id.fab)
    var mFabGroup: FloatActBtnGroup? = null

    internal var mNavHeadView: View? = null
    internal var portrait: CircleImageView? = null
    internal var name: TextView? = null
    internal var Email: TextView? = null

    val mNavView: NavigationView by bindView(R.id.nav_view)
    val mDrawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
    val mTlMain: TabLayout by bindView(R.id.tl_main)
    val mVpMain: ViewPager by bindView(R.id.vp_main)

    var dbHelper: DBHelper? = null
    var db: SQLiteDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mFabGroup= FloatActBtnGroup(mFab,this)
//        mFabGroup?.addChild("first",R.mipmap.ic_launcher_round, View.OnClickListener {
//            T.show(this,"click child float action button")
//        })
        dbHelper = DBHelper(this, DBConsts.db_name, null, 1)
        db = dbHelper!!.writableDatabase

        mNavHeadView = mNavView.getHeaderView(0)
        val randomBgCreater = RandomBgCreater(null, true, true, null)
        mNavHeadView!!.background = randomBgCreater.getBackground(0, 0.3f)


        portrait = mNavHeadView!!.findViewById(R.id.portrait_nav_head) as CircleImageView
        portrait!!.setOnClickListener(this)
        name = mNavHeadView!!.findViewById(R.id.tv_name_nav_head) as TextView
        Email = mNavHeadView!!.findViewById(R.id.tv_address_nav_head) as TextView

        //        ImageLoaderProxy.getInstance().displayImage(R.mipmap.ufo_ed, portrait, R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar)

        val toggle = ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        mNavView.setNavigationItemSelectedListener(this)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DailyFragment(db!!), "记录")
        adapter.addFragment(JobFragment(), "工作")
//        adapter.addFragment(PlaygroundFragment(), "广场")
        adapter.addFragment(ContactsFragment(db!!), "联系人")
        mVpMain.adapter = adapter
        mTlMain.setupWithViewPager(mVpMain)
        mVpMain.offscreenPageLimit = 3
        mFab.setOnClickListener {

            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val options = ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab!!.transitionName)
//                startActivity(Intent(this, NotifyActivity::class.java), options.toBundle())
//            } else {
//                startActivity(Intent(this, NotifyActivity::class.java))
//            }
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_seach) {
            startActivity(Intent(this, MainSeachActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_line -> startActivity(Intent(this, LinesActivity::class.java))
            R.id.nav_gank -> startActivity(Intent(this, GankActivity::class.java))
            R.id.nav_setting -> startActivity(Intent(this, SettingsActivity::class.java))
//            R.id.nav_camera-> startActivity(Intent(this, CameraActivity::class.java))
            R.id.nav_camera -> startActivity(Intent(this, Camera2Activity::class.java))
            R.id.nav_ver -> startActivity(Intent(this, HorizontalScrollViewTestActivity::class.java))
            R.id.nav_diy -> startActivity(Intent(this, DiyViewsActivity::class.java))
            R.id.nav_sensor -> startActivity(Intent(this, SensorActivity::class.java))
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    val titles: ArrayList<String>
        get() {
            val titles = ArrayList<String>()
            titles.add("recycle view")
            return titles
        }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.portrait_nav_head -> startActivity(Intent(this@MainActivity, UserCenterActivity::class.java))
        }
    }
}