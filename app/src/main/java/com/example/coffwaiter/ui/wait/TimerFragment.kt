import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coffwaiter.GlobalData
import com.example.coffwaiter.databinding.FragmentTimerBinding
import com.example.coffwaiter.ui.wait.OrderCompleteFragment
import com.example.coffwaiter.ui.wait.WaitActivity

class TimerFragment : Fragment() {

    private lateinit var timer: CountDownTimer
    private var timerLength: Int = 0

    private var timerMinutes: Int = 0

    private var _binding: FragmentTimerBinding? = null
    private val binding: FragmentTimerBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)

        setTimerLength()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        startTimer()
    }

    private fun startTimer(){
        timer = object : CountDownTimer(timerLength.toLong() * 1000, 1000) {
            override fun onFinish() {
                (requireActivity() as WaitActivity).openFragment(OrderCompleteFragment())
            }

            override fun onTick(left: Long) {
                timerMinutes = (left / 1000).toInt()
                updateCountdownUI()
            }
        }.start()
    }

    private fun setTimerLength(){
        for (food in GlobalData.cartFoods)
            timerLength += food.time * food.count

        binding.timerProgressPb.max = timerLength
    }

    private fun updateCountdownUI(){
        binding.timerTv.text = "$timerMinutes ${minStr(timerMinutes)}"
        binding.timerProgressPb.progress = timerLength - timerMinutes
    }

    private fun minStr(num: Int): String {
        if (num in 11..19) return "минут"

        val n = num % 10

        return if (n == 0 || n in 5..9)
            "минут"
        else if (n in 2..4)
            "минуты"
        else "минута"
    }
}