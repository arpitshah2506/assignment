package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductSeller;
import com.example.demo.model.ProductDTO;
import com.example.demo.model.ProductSellerDTO;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSellerRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductSellerRepository productSellerRepository;
	
	@Value("${product.page.size}")
	private int productPageSize;
	
	public List<ProductDTO> getProducts(int pageNumber) {
		List<Product> products = new ArrayList<>();
		PageRequest paging = PageRequest.of(pageNumber, productPageSize);
		
		productRepository.findAll(paging).forEach(product -> products.add(product));
		
		List<ProductDTO> productModels = new ArrayList<>();
		if (products.isEmpty() == false)
		{
			convertProductEntityToDTO(products, productModels);
		}
		
		return productModels;
	}
	
	private void convertProductEntityToDTO(List<Product> products, List<ProductDTO> productModels) {
		for (Product product : products) {
			ProductDTO productModel = new ProductDTO();
			productModel.setPrice(product.getPrice());
			productModel.setDescription(product.getDescription());
			productModel.setProductName(product.getProductName());
			
			productModels.add(productModel);
		}
	}

	private void convertProductDTOToEntity(List<Product> products, List<ProductDTO> productDTOs) {
		for (ProductDTO productDTO : productDTOs) {
			Product productEntity = new Product();
			productEntity.setPrice(productDTO.getPrice());
			productEntity.setDescription(productDTO.getDescription());
			productEntity.setProductName(productDTO.getProductName());
			
			products.add(productEntity);
		}
	}

	public boolean addProducts(ProductSellerDTO sellerProducts) {
		long totalAvailableProducts = productRepository.count();
		
		List<Product> products = new ArrayList<Product>();
		convertProductDTOToEntity(products, sellerProducts.getProducts());
		
		productRepository.saveAll(products);
		
		long newCount = productRepository.count();
		
		List<ProductSeller> salerProducts = new ArrayList<>();
		for (int index = 0; index < (newCount - totalAvailableProducts); index++) {
			ProductSeller salerProduct = new ProductSeller();
			salerProduct.setProductId(totalAvailableProducts + index + 1);
			salerProduct.setUserId(sellerProducts.getUserId());
			
			salerProducts.add(salerProduct);
		}
		
		if (salerProducts.size() > 0) {
			productSellerRepository.saveAll(salerProducts);
		}
		
		return true;
	}
}
