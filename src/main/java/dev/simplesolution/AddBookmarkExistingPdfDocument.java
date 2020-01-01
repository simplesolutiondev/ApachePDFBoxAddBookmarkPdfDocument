package dev.simplesolution;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PageMode;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

public class AddBookmarkExistingPdfDocument {
	
	public static void main(String[] args) {
		try (PDDocument document = PDDocument.load(new File("D:\\SimpleSolution\\Document.pdf"))) {
			PDDocumentOutline documentOutline = new PDDocumentOutline();
			document.getDocumentCatalog().setDocumentOutline(documentOutline);
			PDOutlineItem pagesOutline = new PDOutlineItem();
			pagesOutline.setTitle("All Pages");
			documentOutline.addLast(pagesOutline);
			
			for(int i = 0; i < document.getNumberOfPages(); i++) {
				PDPageDestination pageDestination = new PDPageFitWidthDestination();
				pageDestination.setPage(document.getPage(i));
				
				PDOutlineItem bookmark = new PDOutlineItem();
				bookmark.setDestination(pageDestination);
				bookmark.setTitle("Document Page " + (i + 1));
				pagesOutline.addLast(bookmark);
			}

			pagesOutline.openNode();
			documentOutline.openNode();
			
			document.getDocumentCatalog().setPageMode(PageMode.USE_OUTLINES);

			document.save("D:\\SimpleSolution\\Document.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
