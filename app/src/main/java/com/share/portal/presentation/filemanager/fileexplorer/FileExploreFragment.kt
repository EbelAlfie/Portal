package com.share.portal.presentation.filemanager.fileexplorer

//
//class FileExploreFragment: ProgenitorFragment<FragmentFileExplorerBinding>() {
//
//  @Inject
//  lateinit var viewModel: FileViewModel
//
//  @Inject
//  lateinit var fileProcessor: FileProcessor
//
//  private val fileAdapter: FileAdapter by lazy { FileAdapter() }
//  private val parentAdapter: ParentAdapter by lazy { ParentAdapter() }
//
//  override fun initBinding(layoutInflater: LayoutInflater) =
//    FragmentFileExplorerBinding.inflate(layoutInflater)
//
//  override fun initFragment() {
//    fragmentComponent.inject(this)
//    fileProcessor.init(fileAdapter, viewModel)
//    registerObservers()
//    setupView()
//  }
//
//  override fun onBackPressed() {
//    fileProcessor.onBackPressed {
//      val currentRoot = parentAdapter.getCurrentNode().substringBeforeLast("/")
//      if (currentRoot.isNotBlank()) fileProcessor.traverseFile(currentRoot)
//      else requireActivity().finish()
//    }
//  }
//
//  private fun registerObservers() {
//    lifecycleScope.launch {
//      viewModel.fileData.collectLatest {
//        if (it == null) return@collectLatest
//        loadData(it)
//      }
//    }
//    lifecycleScope.launch {
//      viewModel.errorFile.collectLatest {
//        if (it == null) return@collectLatest
//        showErrorDialog(it)
//      }
//    }
//  }
//
//  private fun setupView() {
//    fileProcessor.setAdapterListener()
//    parentAdapter.setListener(fileProcessor::traverseFile)
//
//    binding.run {
//      rvFiles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//      rvFiles.adapter = fileAdapter
//
//      rvParent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//      rvParent.adapter = parentAdapter
//    }
//  }
//
//  private fun loadData(data: FileTreeEntity) {
//    parentAdapter.update(data.current)
//    fileAdapter.updateList(data)
//  }
//
//  private fun showErrorDialog(throwable: Throwable) {
//    showToast(throwable.message)
//  }
//}