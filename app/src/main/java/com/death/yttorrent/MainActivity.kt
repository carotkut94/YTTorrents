package com.death.yttorrent

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.death.yttorrent.adapter.MoviesAdapter
import com.death.yttorrent.model.movies.MoviesItem
import com.death.yttorrent.util.GridItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import com.github.se_bastiaan.torrentstream.TorrentStream
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.github.se_bastiaan.torrentstream.TorrentOptions
import com.github.se_bastiaan.torrentstream.listeners.TorrentListener
import java.lang.Exception
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.requestPermissions
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {

    private val ytsApiService by lazy {
        YTSApiService.create()
    }

    private var disposable: Disposable? = null
    private var linearLayout: GridLayoutManager? = null
    private var toolBar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar = toolbar
        setSupportActionBar(toolbar)

        toolBar!!.title = "YTTorrents"
        toolBar!!.setTitleTextColor(Color.WHITE)

        fetchMoviesAndFillData(1)
        linearLayout = GridLayoutManager(this, 2)
    }

    private fun fetchMoviesAndFillData(page: Int) {
        disposable = ytsApiService.getMovies(8, 50, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("Message", result.statusMessage)
                            recyclerView.layoutManager = linearLayout
                            recyclerView.addItemDecoration(GridItemDecoration(2, 3, true))
                            recyclerView.adapter = MoviesAdapter(MainActivity@ this, result.data!!.movies as List<MoviesItem>?)
                            recyclerView.addOnItemTouchListener(MoviesAdapter.RecyclerTouchListener(
                                    this,
                                    recyclerView,
                                    object : MoviesAdapter.ClickListener {
                                        override fun onClick(view: View, position: Int) {
                                            if(isStoragePermissionGranted()) {
                                                Toast.makeText(view.context, result.data.movies!![position]!!.title, LENGTH_LONG).show()
                                                val torrentOptions = TorrentOptions.Builder()
                                                        .saveLocation(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                                                        .removeFilesAfterStop(true)
                                                        .build()

                                                val torrentStream = TorrentStream.init(torrentOptions)
                                                torrentStream.startStream(result.data.movies[position]!!.torrents!![0]!!.url)

                                                torrentStream.addListener(object : TorrentListener {
                                                    override fun onStreamReady(p0: Torrent?) {
                                                        Log.e("Ready", "True")
                                                        Log.d("READY", "onStreamReady: " + p0!!.videoFile)
                                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(p0!!.videoFile.toString()))
                                                        intent.setDataAndType(Uri.parse(p0!!.videoFile.toString()), "video/mp4")
                                                        startActivity(intent)
                                                    }

                                                    override fun onStreamPrepared(p0: Torrent?) {
                                                        Log.e("Prepared", "True")
                                                    }

                                                    override fun onStreamStopped() {
                                                        Log.e("Stopped", "True")
                                                    }

                                                    override fun onStreamStarted(p0: Torrent?) {
                                                        Log.e("Started", "True")
                                                    }

                                                    override fun onStreamProgress(p0: Torrent?, p1: StreamStatus?) {
                                                        Log.e("Progress", "" + p1!!.progress)
                                                    }

                                                    override fun onStreamError(p0: Torrent?, p1: Exception?) {
                                                        Log.e("Error", "True")
                                                        p1!!.printStackTrace()
                                                    }

                                                })
                                            }
                                        }

                                        override fun onLongClick(view: View?, position: Int) {

                                        }
                                    }
                            ))
                        },
                        { error ->
                            Log.e("ERROR", error.localizedMessage)
                            error.printStackTrace()
                            Toast.makeText(MainActivity@ this, error.message, LENGTH_LONG).show()
                        }
                )

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i("Denied", "Permission has been denied by user")
                } else {
                    Log.i("Granted", "Permission has been granted by user")
                }
            }
        }
    }

    fun isStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Status", "Permission is granted")
                return true
            } else {
                requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 1)
                return false;
            }
        } else {
            Log.v("GRANTED", "Permission is granted")
            return true
        }
    }
}
