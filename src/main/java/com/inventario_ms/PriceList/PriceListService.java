package com.inventario_ms.PriceList;

import com.inventario_ms.Generic.GenericService;
import com.inventario_ms.Product.Product;
import com.inventario_ms.Product.ProductService;
import com.inventario_ms.Supplier.SupplierService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceListService extends GenericService<PriceList,PriceListDTO,Long> {
    private final ProductService productService;
    private final PriceListRepository priceListRepository;
    private final SupplierService supplierService;

    public PriceListService(PriceListRepository priceListRepository,
                            ProductService productService,
                            SupplierService supplierService) {
        super(priceListRepository);
        this.productService = productService;
        this.priceListRepository = priceListRepository;
        this.supplierService = supplierService;

    }

    @Override
    protected PriceList updateEntity(PriceList entity, PriceList updatedEntity) {
        return null;
    }

    @Override
    protected PriceListDTO convertToDTO(PriceList entity) {
        PriceListDTO dto = new PriceListDTO();
        dto.setFechaFinVigencia(entity.getFechaFinVigencia());
        dto.setFechaInicioVigencia(entity.getFechaInicioVigencia());
        dto.setSupplier(entity.getSupplier());
        dto.setPriceListProducts(entity.getPriceListProducts());
        return dto;
    }
    public PriceListDTO uploadPriceList(Long supplierId, MultipartFile file) {
        PriceList priceList = new PriceList();
        List<PriceListProduct> priceListProducts = new ArrayList<>();
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            Row r = sheet.getRow(2);
            if (r != null) {
                priceList.setFechaInicioVigencia(LocalDate.now());
                priceList.setFechaFinVigencia(r.getCell(1).getLocalDateTimeCellValue().toLocalDate());
            }

            for(Row row: sheet){
                if (row.getRowNum() < 5) {
                    continue;
                }
                PriceListProduct priceListProduct = new PriceListProduct();

                Optional<Product> optional = productService.findById((long) row.getCell(0).getNumericCellValue());
                priceListProduct.setProduct(optional.orElse(null));

                priceListProduct.setPrecio(row.getCell(3).getNumericCellValue());
                priceListProduct.setCantidad((int) row.getCell(4).getNumericCellValue());
                String promotion= row.getCell(5).getStringCellValue();
                if(promotion.equals("Si")) priceListProduct.setPromocion(true);
                else if (promotion.equals("No")) {
                    priceListProduct.setPromocion(false);
                }

                priceListProduct.setPriceList(priceList);
                priceListProducts.add(priceListProduct);
            }


            priceList.setPriceListProducts(priceListProducts);
            priceList.setSupplier(supplierService.findById(supplierId).orElseThrow());

            PriceListDTO priceListDTO = convertToDTO(priceList);

            priceListRepository.save(priceList);
            return priceListDTO;
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }

    }

    public PriceList findBySupplierAndDate(Long supplierId) {
        return priceListRepository.findBySupplierAndDate(supplierId);
    }
}
