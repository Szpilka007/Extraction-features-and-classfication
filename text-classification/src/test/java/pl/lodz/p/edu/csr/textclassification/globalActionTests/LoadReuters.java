package pl.lodz.p.edu.csr.textclassification.globalActionTests;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import pl.lodz.p.edu.csr.textclassification.model.ElementType;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.XmlExtractor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadReuters {

    @Test
    @Ignore // Only manual run! This test load reuters to database!
    void loadSpecificReuters() {

    }

    @Test
    @Ignore // Only manual run! This test load ALL reuters to database!
    void loadAllReuters() {

    }

}