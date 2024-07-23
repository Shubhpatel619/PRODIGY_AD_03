package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private var startTime = 0L
    private var elapsedTime = 0L
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runnable = Runnable {
            elapsedTime = System.currentTimeMillis() - startTime
            updateTimerText(elapsedTime)
            handler.postDelayed(runnable, 10)
        }

        binding.btnStartPause.setOnClickListener {
            if (isRunning) {
                pauseStopwatch()
            } else {
                startStopwatch()
            }
        }

        binding.btnReset.setOnClickListener {
            resetStopwatch()
        }
    }

    private fun startStopwatch() {
        binding.btnStartPause.text = "Pause"
        startTime = System.currentTimeMillis() - elapsedTime
        handler.post(runnable)
        isRunning = true
    }

    private fun pauseStopwatch() {
        binding.btnStartPause.text = "Start"
        handler.removeCallbacks(runnable)
        isRunning = false
    }

    private fun resetStopwatch() {
        handler.removeCallbacks(runnable)
        isRunning = false
        elapsedTime = 0
        updateTimerText(elapsedTime)
        binding.btnStartPause.text = "Start"
    }

    private fun updateTimerText(elapsedTime: Long) {
        val minutes = (elapsedTime / 1000) / 60
        val seconds = (elapsedTime / 1000) % 60
        val milliseconds = (elapsedTime % 1000)
        binding.tvTimer.text = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
    }
}
