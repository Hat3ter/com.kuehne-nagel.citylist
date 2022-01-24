package db.migration;

import com.solbegsoft.citylist.models.entities.City;
import com.solbegsoft.citylist.utils.CsvReader;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Migration for filling from csv
 */
@Slf4j
public class V1_1_1__fill_cities extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {

        Connection connection = context.getConnection();

        String query = "insert into cities(id, name, photo_path) values(uuid_generate_v4(), ?,?) on conflict do nothing";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        Resource resource = new ClassPathResource("cities.csv");
        InputStream inputStream = resource.getInputStream();
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        List<City> cities = CsvReader.convertToCityList(CsvReader.read(reader));
        log.info("Loaded from csv {} cities ignore duplicates", cities.size());
        for (City city : cities) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getPhotoPath());
            preparedStatement.execute();
        }
    }
}
