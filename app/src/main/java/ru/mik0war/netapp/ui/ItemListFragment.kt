package ru.mik0war.netapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import ru.mik0war.netapp.data.RepositoryImpl
import ru.mik0war.netapp.data.cloud.CloudDataSourceImpl
import ru.mik0war.netapp.data.cloud.NetApp
import ru.mik0war.netapp.data.cloud.ServerDTO
import ru.mik0war.netapp.databinding.FragmentItemListListBinding
import ru.mik0war.netapp.domain.ItemInteractor
import ru.mik0war.netapp.domain.ItemModel
import ru.mik0war.netapp.utils.DispatcherManager
import ru.mik0war.netapp.utils.ItemMapper


class ItemViewModelFactory: ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val mapperToData = object : ItemMapper<ServerDTO>{
            override fun map(
                id: Int,
                name: String
            ) = ServerDTO(name, id)

            override fun mapError(text: String) = throw IllegalStateException()

        }

        val mapperToDomain = object : ItemMapper<ItemModel> {
            override fun map(
                id: Int,
                name: String
            ) = ItemModel.Success(name, id)

            override fun mapError(text: String) = ItemModel.Error(text)
        }

        val mapperToUI = object : ItemMapper<ItemUIState> {
            override fun map(
                id: Int,
                name: String
            ) = ItemUIState.Success(id, name)

            override fun mapError(text: String) = ItemUIState.Error(text)
        }

        val dispatcherManager = object : DispatcherManager {
            override fun io() = Dispatchers.IO

            override fun main() = Dispatchers.Main
        }

        return ItemListViewModel(
            ItemInteractor(
                RepositoryImpl(
                    CloudDataSourceImpl(
                        NetApp.api,
                        mapperToData,
                        mapperToDomain
                    ),
                    dispatcherManager
                )
            ),
            ItemLiveData(),
            dispatcherManager,
            mapperToUI
        ) as T
    }
}

class ItemListFragment : Fragment() {

    private var _binding: FragmentItemListListBinding? = null

    private val viewModel: ItemListViewModel by viewModels{
        ItemViewModelFactory()
    }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemRecyclerViewAdapter()

        binding.list.adapter = adapter

        viewModel.observe(viewLifecycleOwner, adapter)

        viewModel.showList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}