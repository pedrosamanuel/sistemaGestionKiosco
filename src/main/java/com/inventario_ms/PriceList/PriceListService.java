package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Product.Product;
import com.inventario_ms.Product.ProductRepository;
import com.inventario_ms.Supplier.Supplier;
import com.inventario_ms.Supplier.SupplierRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceListService extends GenericService<PriceList,PriceListDTO,Long> {
    private final ProductRepository productRepository;
    private final PriceListRepository priceListRepository;
    private final SupplierRepository supplierRepository;

    public PriceListService(PriceListRepository priceListRepository,
                            ProductRepository productRepository,
                            SupplierRepository supplierRepository) {
        super(priceListRepository);
        this.productRepository = productRepository;
        this.priceListRepository = priceListRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    protected PriceList updateEntity(PriceList entity, PriceList updatedEntity) {
        return null;
    }

    @Override
    protected PriceListDTO convertToDTO(PriceList entity) {
        PriceListDTO dto = new PriceListDTO();
        dto.setFechaFinvigencia(entity.getFechaFinvigencia());
        dto.setFechaInicioVigencia(entity.getFechaInicioVigencia());
        dto.setSupplier(entity.getSupplier());
        dto.setPriceListProducts(entity.getPriceListProducts());
        return dto;
    }
    public PriceListDTO uploadPriceList(Long supplierId, MultipartFile file) {
        PriceListDTO priceListDTO = new PriceListDTO();
        PriceList priceList = new PriceList();
        List<PriceListProduct> priceListProducts = new ArrayList<>();
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(1);
            for(Row row: sheet){
                if (row.getRowNum() == 0) {
                    continue;
                }
                PriceListProduct priceListProduct = new PriceListProduct();

                Optional<Product> optional = productRepository.findById((long) row.getCell(0).getNumericCellValue());
                priceListProduct.setProduct(optional.orElse(null));

                priceListProduct.setPrecio(row.getCell(3).getNumericCellValue());
                priceListProduct.setCantidad((int) row.getCell(4).getNumericCellValue());
                priceListProduct.setPromocion(row.getCell(5).getBooleanCellValue());
                priceListProduct.setPriceList(priceList);
                priceListProducts.add(priceListProduct);
            }
            sheet = workbook.getSheetAt(0);
            Row r = sheet.getRow(1);
            if (r != null) {
                priceList.setFechaInicioVigencia(r.getCell(0).getLocalDateTimeCellValue().toLocalDate());
                priceList.setFechaFinvigencia(r.getCell(1).getLocalDateTimeCellValue().toLocalDate());
            }

            priceList.setPriceListProducts(priceListProducts);
            priceList.setSupplier(supplierRepository.findById(supplierId).orElseThrow());

            priceListDTO = convertToDTO(priceList);

            priceListRepository.save(priceList);
            return priceListDTO;
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }

    }
}
