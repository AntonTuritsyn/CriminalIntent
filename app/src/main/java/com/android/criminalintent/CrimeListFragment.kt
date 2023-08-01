package com.android.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.launch


class CrimeListFragment: Fragment() {

    private var _binding: FragmentCrimeListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val crimeListViewModel: CrimeListViewModel by viewModels()

//      Не нужно
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
//      Установка LayoutManager для отображения элементов в RecyclerView. Используется LinearLayout для вертикального отображения
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)

//      После создания адаптера создаем экземпляр с данными о преступлении и подключаем его к RecyclerView.
//      Удаляется после добавления repeatOnLifecycle в onViewCreated
        /*val crimes = crimeListViewModel.crimes
        val adapter = CrimeListAdapter(crimes)
        binding.crimeRecyclerView.adapter = adapter*/

        return binding.root
    }
/*      Код будет вести себя точно так же, как и с onStart() и onStop(),
        но теперь необходимо переопределять меньше методов жизненного цикла.
        repeatOnLifecycle(...) начнет выполнять код сопрограммы, когда фрагмент перейдет в запущенное состояние, и продолжит работу в возобновленном состоянии.
        Но если приложение работает в фоновом режиме и фрагмент больше не отображается, repeatOnLifecycle(...) отменит работу, как только фрагмент перейдет из начального состояния в созданное.
        Если жизненный цикл снова войдет в запущенное состояние без полного уничтожения, сопрограмма будет перезапущена с самого начала, повторяя свою работу.*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

//              Используется collect для сбора данных о преступлениях из потока и обновления пользовательского интерфейса
                crimeListViewModel.crimes.collect {crimes ->
                    binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes) {crimeId ->

/*                      Используется NavController для для навигации по фрагментам (перекючение на следующий фрагмент при нажатии на элемент списка)
                        Для генерации классов для фрагментов используется Safe Args (аналогично как ViewBinding)*/
                        findNavController().navigate(CrimeListFragmentDirections.showCrimeDetail(crimeId))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}