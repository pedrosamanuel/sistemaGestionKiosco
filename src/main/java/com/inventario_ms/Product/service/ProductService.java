package com.inventario_ms.Product.service;

import com.inventario_ms.Generic.NonDependent.NonDependentGenericService;
import com.inventario_ms.Product.domain.Product;
import com.inventario_ms.Product.dto.ProductDTO;
import com.inventario_ms.Product.repository.ProductRepository;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ProductService extends NonDependentGenericService<Product, ProductDTO,ProductRepository, Long> {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }

    @Override
    protected Product updateEntity(Product entity, Product updatedEntity) {
        entity.setDescripcion(updatedEntity.getDescripcion());
        entity.setCodigo(updatedEntity.getCodigo());
        return entity;
    }

    @Override
    protected ProductDTO convertToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }
    @Override
    public void save(Product product){
        Optional<Product> optionalProduct =
                productRepository.findByCodigo(product.getCodigo());
        if (optionalProduct.isEmpty()){
            productRepository.save(product);
        }
    }

    public Optional<Product> findByCodigo(String codigo) {
        return productRepository.findByCodigo(codigo);
    }

    public boolean uploadProducts(MultipartFile file) {
        Set<String> existingCodes = productRepository.findAllCodigos();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("id") || line.startsWith("Ultima")
                        || ((line.split("\\|").length) < 2)) {
                    continue;
                }

                String[] lineProductArray = line.split("\\|");

                if (lineProductArray[4].equals("1") && !existingCodes.contains(lineProductArray[3])) {
                    Product product = new Product();
                    product.setCodigo(lineProductArray[3]);
                    product.setDescripcion(lineProductArray[5]);
                    productRepository.save(product);
                    existingCodes.add(lineProductArray[3]);
                }
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo CSV", e);
        }
}
}
