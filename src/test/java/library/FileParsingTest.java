package library;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import library.model.booksGlossary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTest {

    private ClassLoader cl = FileParsingTest.class.getClassLoader();
    private static final Gson gson = new Gson();

    @Test
    void csvFileParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("book-inventory.csv");
             CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {
            List<String[]> data = csvReader.readAll();
            Assertions.assertEquals(2, data.size());
            Assertions.assertArrayEquals(
                    new String[]{"firstCount", "secondCount", "expectedTotal"},
                    data.get(0)
            );
            Assertions.assertArrayEquals(
                    new String[]{"1", "2", "3"},
                    data.get(1)
            );
        }

    }

    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("library/testdata/library.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());  //читать имена файлов и проверял что в архиве те файлы, что нужны
            }

        }

    }


    @Test
    void jsonFileParsingImprovedTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("library/testdata/books-glossary.json")
        )) {
            JsonObject actual = gson.fromJson(reader, JsonObject.class);   //на Jakson

            Assertions.assertEquals("Demo Library", actual.get("libraryName").getAsString());
            Assertions.assertEquals("2026-04-08", actual.get("updatedAt").getAsString());

            JsonObject inner = actual.get("books").getAsJsonObject();

            Assertions.assertEquals("B001", inner.get("id").getAsString());
            Assertions.assertEquals("Clean Code", inner.get("title").getAsString());
            Assertions.assertEquals("Robert C. Martin", inner.get("author").getAsString());

        }

    }
}
