package org.vaadin.spring.tutorial;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.AxisType;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataProviderSeries;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.spring.tutorial.Models.PrismaChartDocumentsOnPrinters;

@PageTitle("Prisma Chart View")
@Route(value = "prisma/chart")
public class PrismaChartView extends VerticalLayout {
    Chart chart = new Chart(ChartType.COLUMN);
    Configuration conf = chart.getConfiguration();
    private final PrismaService prismaService;

    public PrismaChartView(PrismaService prismaService) {
        this.prismaService = prismaService;

        conf.setTitle("Documents printed");
        getChartData();
        add(chart);
    }

    private void getChartData() {
        DataProvider<PrismaChartDocumentsOnPrinters, ?> dataProvider = new ListDataProvider<>(prismaService.getChartData());
        DataProviderSeries<PrismaChartDocumentsOnPrinters> series = new DataProviderSeries<>(dataProvider, PrismaChartDocumentsOnPrinters::getDocumentsPrinted);
        series.setName("Printers");
        series.setX(PrismaChartDocumentsOnPrinters::getPrinter);
        conf.getxAxis().setType(AxisType.CATEGORY);
        conf.addSeries(series);

        Grid<PrismaChartDocumentsOnPrinters> grid = new Grid<>();
        grid.addColumn(PrismaChartDocumentsOnPrinters::getYear).setHeader("Year");
        grid.addColumn(PrismaChartDocumentsOnPrinters::getPrinter).setHeader("Printer");
        grid.addColumn(PrismaChartDocumentsOnPrinters::getDocumentsPrinted).setHeader("Documents Printed");
        grid.setItems(prismaService.getChartData());
        add(grid);
    }
}
