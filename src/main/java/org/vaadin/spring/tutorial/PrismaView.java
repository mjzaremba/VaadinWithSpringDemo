package org.vaadin.spring.tutorial;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.tutorial.Models.PrismaEntry;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@PageTitle("Prisma")
@Route(value = "prisma")
public class PrismaView extends VerticalLayout {
    Button btnClearDatabase = new Button("Clear Database");
    Button btnSaveToDatabase = new Button("Save records to Database");
    Label gridLabel = new Label("Displaying records from Database:");
    FileBuffer fileBuffer = new FileBuffer();
    Upload upload = new Upload(fileBuffer);
    FileInputStream fileInputStream;
    VerticalLayout vlTop;
    VerticalLayout vlMain;
    HorizontalLayout vlBottom;
    Grid<PrismaEntry> grid;
    private final PrismaService prismaService;

    @Autowired
    public PrismaView(PrismaService prismaService) {
        this.prismaService = prismaService;
        vlTop = new VerticalLayout();
        vlTop.setHeight("10%");

        List<PrismaEntry> recordsFromFile = new ArrayList<>();
        upload.addSucceededListener(event -> {
            fileInputStream = (FileInputStream) fileBuffer.getInputStream();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
                String yearFromFile = fileBuffer.getFileName().replaceAll("\\..*", "");
                String line;
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(";");

                    if (arr[0].equals("1031") && arr[8].matches("[A-Z]{3}_P_P_\\d{4}_\\d{8}_\\[.*]")) {
                        PrismaEntry newEntry = new PrismaEntry();
                        String[] filename = arr[8].split("[_\\[\\]]");

                        newEntry.setIdNumber(Integer.parseInt(arr[0]));
                        newEntry.setPrinter(arr[4]);
                        newEntry.setFilename(arr[8]);
                        newEntry.setClientName(filename[0]);
                        newEntry.setSla(filename[3]);
                        newEntry.setPosyId(filename[6]);
                        newEntry.setDocumentsCount(Integer.parseInt(filename[8]));
                        newEntry.setStaple(filename[9]);
                        newEntry.setYear(yearFromFile);

                        recordsFromFile.add(newEntry);
                    }
                }
                gridLabel.setText("Displaying records from uploaded file:");
                grid.setItems(recordsFromFile);
                btnSaveToDatabase.setEnabled(true);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        vlTop.add(upload);

        //Main
        vlMain = new VerticalLayout();
        vlMain.setHeight("80%");

        grid = new Grid<>(PrismaEntry.class, false);
        grid.addColumn(PrismaEntry::getYear).setHeader("Year").setWidth("7%");
        grid.addColumn(PrismaEntry::getIdNumber).setHeader("Id Number").setWidth("7%");
        grid.addColumn(PrismaEntry::getPrinter).setHeader("Printer").setWidth("41%");
        grid.addColumn(PrismaEntry::getFilename).setHeader("Filename").setWidth("45%");
        grid.setItems(this.prismaService.findAll());
        vlMain.add(gridLabel, grid);

        grid.addItemDoubleClickListener(event -> {
            PrismaEntry entry = event.getItem();
            Notification.show("Client Name: " + entry.getClientName()
                            + " SLA: " + entry.getSla()
                            + " PosyId: " + entry.getPosyId()
                            + " No. Documents: " + entry.getDocumentsCount()
                            + " Staple: " + entry.getStaple(),
                    5000, Notification.Position.BOTTOM_CENTER
            );
        });

        //Bottom
        vlBottom = new HorizontalLayout();
        vlBottom.setHeight("10%");
        vlBottom.add(btnClearDatabase, btnSaveToDatabase);

        btnClearDatabase.addClickListener(e -> {
            this.prismaService.clearDatabase();
            grid.setItems(this.prismaService.findAll());
        });
        btnSaveToDatabase.setEnabled(false);
        btnSaveToDatabase.addClickListener(e -> {
            this.prismaService.saveAll(recordsFromFile);
            recordsFromFile.clear();
            btnSaveToDatabase.setEnabled(false);
            grid.setItems(this.prismaService.findAll());
        });

        add(vlTop, vlMain, vlBottom);
    }
}

