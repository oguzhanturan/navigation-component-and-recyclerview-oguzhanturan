package school.cactus.succulentshop.product.detail

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import school.cactus.succulentshop.R
import school.cactus.succulentshop.databinding.FragmentProductDetailBinding
import school.cactus.succulentshop.product.BUNDLE_KEY_PRODUCT_ID
import school.cactus.succulentshop.product.list.ProductDecoration
import school.cactus.succulentshop.product.list.ProductListFragmentDirections
import school.cactus.succulentshop.product.list.ProductStore

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null

    private val binding get() = _binding!!

    private val adapter = RelatedProductAdapter()

    private val store = ProductStore()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.app_name)

        val productId = requireArguments().getInt(BUNDLE_KEY_PRODUCT_ID)
        val product = store.findProduct(productId)

        binding.relatedProductRecyclerView.adapter = adapter
        binding.relatedProductRecyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.relatedProductRecyclerView.addItemDecoration(RelatedProductDecoration())
        adapter.submitList(store.relatedProducts(productId))

        adapter.itemClickListener = {
            val action = ProductDetailFragmentDirections.openProductDetailFragmentSelf(it.id)
            findNavController().navigate(action)
        }

        binding.apply {
            imageView.setImageResource(product.imageUrl)
            titleText.text = product.title
            priceText.text = product.price
            descriptionText.text = product.description
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}