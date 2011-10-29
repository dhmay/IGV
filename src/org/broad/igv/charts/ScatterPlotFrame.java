/*
 * Created by JFormDesigner on Wed Oct 26 22:35:11 EDT 2011
 */

package org.broad.igv.charts;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

import org.broad.igv.scatterplot.ScatterPlotData;

/**
 * @author Stan Diamond
 */
public class ScatterPlotFrame extends JFrame {


    ScatterPlotData scatterPlotData;

    public ScatterPlotFrame() {
        initComponents();
    }

    public ScatterPlotFrame(ScatterPlotData scatterPlotData) {

        this.scatterPlotData = scatterPlotData;

        initComponents();

        List<String> categoryList = scatterPlotData.getCategories();
        if (categoryList.size() > 0) {
            String[] categories = categoryList.toArray(new String[categoryList.size()]);
            classifyComboBox.setModel(new DefaultComboBoxModel(categories));
        } else {
            classifyComboBox.setEnabled(false);
        }

        List<String> dataTypeList = scatterPlotData.getDataNames();
        String[] dataTypes = dataTypeList.toArray(new String[dataTypeList.size()]);
        xAxisComboBox.setModel(new DefaultComboBoxModel(dataTypes));
        yAxisComboBox.setModel(new DefaultComboBoxModel(dataTypes));

        classifyComboBox.setSelectedIndex(0);
        xAxisComboBox.setSelectedIndex(0);
        yAxisComboBox.setSelectedIndex(dataTypes.length > 1 ? 1 : 0);
        updateModel();
    }


    private void closeMenuItemActionPerformed(ActionEvent e) {
        setVisible(false);
    }

    private void axisChanged(ActionEvent e) {
       updateModel();
    }

    private void attributeChanged(ActionEvent e) {
       updateModel();
    }

    /*
    *   createChart - method creates a JFree ChartPanel (derived from JPanel)
    *   which plots the XYDataset assigned form ScatterPlotData selections.
    *
    *   Parameters:
    *       title - string contains title of plot
    *       igvData - ScatterPlotFrame container class for IGV data samples
    *       xAxisName - IGV data measurement name for x Axis coordinate;
    *           must be a key defined in igvData dataMap.
    *       yAxisName - IGV data measurement name for y Axis coordinate;
    *           must be a key defined in igvData dataMap.
    *       attribute - sample attribute selected for series symbol display
    *       symbolSettings - symbol settings for series symbol display.
    *
    *   Returns:
    *       Scatterplot chart
    *
    * */


    private void updateModel() {

        String xAxisName = (String) xAxisComboBox.getSelectedItem();
        String yAxisName = (String) yAxisComboBox.getSelectedItem();

        double[] xValues = scatterPlotData.getDataValues(xAxisName);
        double[] yValues = scatterPlotData.getDataValues(yAxisName);

        // check for valid data and axis selection - and if error, return null
        if (yValues == null | xValues == null | yValues.length != xValues.length)
            return;

        // Note: Currently only one attribute can be selected for scatter plot,
        // therefore using the 1st series (key = index 0) to identify the attribute

        String selectedCategory = (String) classifyComboBox.getSelectedItem();
        String[] seriesNames = scatterPlotData.getAttributeCategories(selectedCategory);

        // extract sample categories for the selected attribute name
        String[] attributeValues = scatterPlotData.getSymbolValues(selectedCategory);

        // create series collection to hold xy series datasets for JFreeChart
        XYDataModel model = new XYDataModel();

        for (String series : seriesNames) {
            XYSeries xySeries = new XYSeries(series);

            // create plot series
            for (int dataIndex = 0; dataIndex < xValues.length; ++dataIndex) {
                // if attribute value is same as category - assign data point to the series
                String attributeValue = attributeValues[dataIndex];
                if(attributeValue == null) attributeValue = "";

                if (series.equals(attributeValue)) {
                    // get tooltips and assign data point to series
                    String tooltips = ""; //igvPlotData.getSampleDescription(dataIndex, isHTML);
                    xySeries.add(xValues[dataIndex], yValues[dataIndex], tooltips);
                }
            }

            // add series  dataset to series collection
            model.addSeries(xySeries);
        }

        ScatterPlot scatterPlot = new ScatterPlot();
        scatterPlot.setModel(model);
        chartPanel.setScatterPlotModel(scatterPlot);
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        closeMenuItem = new JMenuItem();
        commandBar = new JPanel();
        panel2 = new JPanel();
        label1 = new JLabel();
        xAxisComboBox = new JComboBox();
        hSpacer1 = new JPanel(null);
        panel3 = new JPanel();
        label2 = new JLabel();
        yAxisComboBox = new JComboBox();
        hSpacer2 = new JPanel(null);
        panel4 = new JPanel();
        label3 = new JLabel();
        classifyComboBox = new JComboBox();
        hSpacer3 = new JPanel(null);
        panel5 = new JPanel();
        locusTextField = new JTextField();
        goButton = new JButton();
        chartPanel = new ChartPanel();
        documentationField = new JTextField();

        //======== this ========
        setFocusTraversalPolicyProvider(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar ========
        {

            //======== fileMenu ========
            {
                fileMenu.setText("File");

                //---- closeMenuItem ----
                closeMenuItem.setText("Close");
                closeMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        closeMenuItemActionPerformed(e);
                    }
                });
                fileMenu.add(closeMenuItem);
            }
            menuBar.add(fileMenu);
        }
        setJMenuBar(menuBar);

        //======== commandBar ========
        {
            commandBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
            commandBar.setLayout(new FlowLayout(FlowLayout.LEFT));

            //======== panel2 ========
            {
                panel2.setBorder(new EtchedBorder());
                panel2.setLayout(new FlowLayout());

                //---- label1 ----
                label1.setText("X:");
                panel2.add(label1);

                //---- xAxisComboBox ----
                xAxisComboBox.setBorder(null);
                xAxisComboBox.setPreferredSize(new Dimension(150, 27));
                xAxisComboBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        axisChanged(e);
                    }
                });
                panel2.add(xAxisComboBox);
            }
            commandBar.add(panel2);
            commandBar.add(hSpacer1);

            //======== panel3 ========
            {
                panel3.setBorder(new EtchedBorder());
                panel3.setLayout(new FlowLayout());

                //---- label2 ----
                label2.setText("Y:");
                panel3.add(label2);

                //---- yAxisComboBox ----
                yAxisComboBox.setPreferredSize(new Dimension(150, 27));
                yAxisComboBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        axisChanged(e);
                    }
                });
                panel3.add(yAxisComboBox);
            }
            commandBar.add(panel3);
            commandBar.add(hSpacer2);

            //======== panel4 ========
            {
                panel4.setBorder(new EtchedBorder());
                panel4.setLayout(new FlowLayout());

                //---- label3 ----
                label3.setText("Classify By:");
                panel4.add(label3);

                //---- classifyComboBox ----
                classifyComboBox.setPreferredSize(new Dimension(150, 27));
                classifyComboBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        attributeChanged(e);
                    }
                });
                panel4.add(classifyComboBox);
            }
            commandBar.add(panel4);
            commandBar.add(hSpacer3);

            //======== panel5 ========
            {
                panel5.setBorder(new EtchedBorder());
                panel5.setLayout(new FlowLayout());

                //---- locusTextField ----
                locusTextField.setPreferredSize(new Dimension(150, 28));
                panel5.add(locusTextField);

                //---- goButton ----
                goButton.setText("Go");
                goButton.setPreferredSize(new Dimension(50, 29));
                goButton.setMinimumSize(new Dimension(50, 29));
                goButton.setMaximumSize(new Dimension(50, 29));
                panel5.add(goButton);
            }
            commandBar.add(panel5);
        }
        contentPane.add(commandBar, BorderLayout.NORTH);
        contentPane.add(chartPanel, BorderLayout.CENTER);

        //---- documentationField ----
        documentationField.setEditable(false);
        contentPane.add(documentationField, BorderLayout.SOUTH);
        setSize(935, 660);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem closeMenuItem;
    private JPanel commandBar;
    private JPanel panel2;
    private JLabel label1;
    private JComboBox xAxisComboBox;
    private JPanel hSpacer1;
    private JPanel panel3;
    private JLabel label2;
    private JComboBox yAxisComboBox;
    private JPanel hSpacer2;
    private JPanel panel4;
    private JLabel label3;
    private JComboBox classifyComboBox;
    private JPanel hSpacer3;
    private JPanel panel5;
    private JTextField locusTextField;
    private JButton goButton;
    private ChartPanel chartPanel;
    private JTextField documentationField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
