package org.shayvin.tourplanner.pdf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.repository.TourMemoryRepository;
import org.shayvin.tourplanner.viewmodel.TabViewModel;

import java.io.IOException;
import java.util.List;

public class PdfBox {

    private final TourMemoryRepository tourMemoryRepository = new TourMemoryRepository();
    private static final Logger logger = LogManager.getLogger(PdfBox.class);

    public PdfBox() {
    }

    public void exportToPDF(String filePath) {
        List<Tour> tours = tourMemoryRepository.findAll();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Title settings
            float titleWidth = new PDType1Font(FontName.HELVETICA_BOLD).getStringWidth("Tour Details") / 1000f * 20;
            float titleXPosition = titleWidth;
            float titleYPosition = page.getMediaBox().getHeight() - 50;

            // Add title
            addTitle("Tour Details", contentStream, page, titleXPosition, titleYPosition);

            // Tour settings
            contentStream.setFont(new PDType1Font(FontName.HELVETICA), 14);
            float startYPosition = titleYPosition - 50;
            float startXPosition = page.getMediaBox().getWidth() / 5;
            float tourHeight = startYPosition;
            int index = 0;

            // Tour content
            for (Tour value : tours) {
                if (tourHeight < 200) {
                    contentStream.close();
                    page = addNewPage(document);
                    contentStream = new PDPageContentStream(document, page);
                    tourHeight = page.getMediaBox().getHeight() - 50;
                    startYPosition = tourHeight;
                    addTitle("Tour Details", contentStream, page, titleXPosition, tourHeight);
                    contentStream.setFont(new PDType1Font(FontName.HELVETICA), 14);
                    startYPosition -= 50;
                    tourHeight = startYPosition;
                }

                contentStream.beginText();
                contentStream.newLineAtOffset(startXPosition, startYPosition);
                contentStream.showText("Name: " + value.getName());
                contentStream.newLine();
                contentStream.showText("Description: " + value.getDescription());
                contentStream.newLine();
                contentStream.showText("Start: " + value.getStart());
                contentStream.newLine();
                contentStream.showText("Destination: " + value.getDestination());
                contentStream.newLine();
                contentStream.showText("Distance: " + value.getDistance());
                contentStream.newLine();
                contentStream.showText("Transport Type: " + value.getType());
                contentStream.newLine();
                contentStream.showText("Duration: " + value.getDuration());
                contentStream.newLine();
                contentStream.showText("Information: " + value.getInformation());
                contentStream.newLine();
                contentStream.showText("Image: " + sliceImageString(value.getImage()));
                contentStream.endText();

                startYPosition -= 150;
                tourHeight = startYPosition;
                index++;
            }

            // Start a new page for Tour Logs
            contentStream.close();
            page = addNewPage(document);
            contentStream = new PDPageContentStream(document, page);

            // Tour Logs title
            titleWidth = new PDType1Font(FontName.HELVETICA_BOLD).getStringWidth("Tour Details") / 1000f * 20;
            titleXPosition = titleWidth;
            titleYPosition = page.getMediaBox().getHeight() - 50;

            addTitle("Tour Logs", contentStream, page, titleXPosition, titleYPosition);

            // Tour Logs settings
            contentStream.setFont(new PDType1Font(FontName.HELVETICA), 14);
            float tourLogsStartYPosition = titleYPosition - 50;
            float tourLogsStartXPosition = page.getMediaBox().getWidth() / 5;
            float tourLogsHeightPosition = tourLogsStartYPosition;
            index = 0;

            // Tour Logs content
            for (Tour value : tours) {
                List<TourLog> tourLogs = value.getTourLogList();

                for (TourLog tourLog : tourLogs) {
                    if (tourLogsStartYPosition < 200) {
                        contentStream.close();
                        page = addNewPage(document);
                        contentStream = new PDPageContentStream(document, page);
                        tourLogsStartXPosition = page.getMediaBox().getWidth() / 5;
                        tourLogsStartYPosition = page.getMediaBox().getHeight() - 50;
                        addTitle("Tour Logs", contentStream, page, titleXPosition, tourLogsStartYPosition);
                        contentStream.setFont(new PDType1Font(FontName.HELVETICA), 14);
                        tourLogsStartYPosition -= 50;
                    }

                    contentStream.beginText();
                    contentStream.newLineAtOffset(tourLogsStartXPosition, tourLogsStartYPosition);
                    contentStream.showText("Tour Name: " + tourLog.getTour().getName());
                    contentStream.newLine();
                    contentStream.showText("Date: " + tourLog.getDate());
                    contentStream.newLine();
                    contentStream.showText("Duration: " + tourLog.getDuration());
                    contentStream.newLine();
                    contentStream.showText("Distance: " + tourLog.getDistance());
                    contentStream.newLine();
                    contentStream.showText("Rating: " + tourLog.getRating());
                    contentStream.newLine();
                    contentStream.showText("Comment: " + tourLog.getComment());
                    contentStream.newLine();
                    contentStream.showText("Difficulty: " + tourLog.getDifficulty());
                    contentStream.endText();

                    tourLogsStartYPosition -= 150;
                    tourLogsHeightPosition = tourLogsStartYPosition;
                    index++;
                }
            }

            contentStream.close();
            logger.info("PDF created successfully!");
            document.save(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PDPage addNewPage(PDDocument document) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        return page;
    }

    private void addTitle(String title, PDPageContentStream contentStream, PDPage page, float titleXPosition, float titleYPosition) throws IOException {
        float titleWidth = new PDType1Font(FontName.HELVETICA_BOLD).getStringWidth(title) / 1000f * 20;

        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 20);
        contentStream.newLineAtOffset(titleXPosition, titleYPosition);
        contentStream.showText(title);
        contentStream.endText();
        // Line
        contentStream.moveTo(titleXPosition, titleYPosition - 10);
        contentStream.lineTo(titleXPosition + titleWidth, titleYPosition - 10);
        contentStream.stroke();
    }

    private String sliceImageString(String image) {
        if (image == null || image.isEmpty()) {
            return "";
        }

        String[] imageArray = image.split("/");
        return imageArray[imageArray.length - 1];
    }
}
