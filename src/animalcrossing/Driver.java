package animalcrossing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;

public class Driver{

    private CardLayout cardLayout;
    private JPanel mainContainer; 
    private SortingAndSearchingSolutions backendEngine;

    private JComboBox<String> fileComboBoxBlathers;
    private JComboBox<String> fileComboBox;

    private JTextField priceInputFieldIsabelle;

    private JButton loadButtonBlathers;
    private JButton loadButton;

    private JButton actionButtonBlathers;
    private JButton actionButton;

    private JButton isabelleNextButton;
    private JButton searchButtonIsabelle;

    private JLabel sortTypeLabelBlathers;
    private JLabel sortTypeLabel;

    private boolean isFileLoadedBlathers = false;
    private boolean isFileLoaded = false;

    private JLabel[] slotsBlathers;
    private JLabel[] slots;

    private JPanel arrayGridBlathers;
    private JPanel arrayGrid;

    private JPanel searchGrid;
    private JLabel[] searchSlots;

     private static final String[] CREATURE_FILES = {
        "creatures1.txt", "creatures2.txt", "creatures3.txt"
    };

    private static final String[] CATALOGUE_FILES = {
        "catalog1.txt", "catalog2.txt", "catalog3.txt"
    };


    public Driver(){
        backendEngine = new SortingAndSearchingSolutions();

        JFrame frame = new JFrame("Animal Crossing: New Sortings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960,540);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        ImageIcon windowIcon = new ImageIcon("assets/leafLogo.png");
        if (windowIcon.getImage() != null){
            frame.setIconImage(windowIcon.getImage());
        }
        else{
            System.out.println("Could not load window icon.");
        }

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        frame.add(mainContainer);

        mainContainer.add(createSelectionScreen(), "SELECT_SCREEN");
        mainContainer.add(createMuseumScreen(), "MUSEUM_SCREEN");
        mainContainer.add(createNookScreen(), "NOOK_SCREEN");
        mainContainer.add(createIsabelleSearchScreen(), "ISABELLE_SEARCH");
        mainContainer.add(createIsabelleFoundScreen(), "ISABELLE_FOUND"); 

        frame.setVisible(true);
        mainContainer.revalidate();
        mainContainer.repaint();
    }


    private JPanel createSelectionScreen(){
        BackgroundPanel panel = new BackgroundPanel("titleScreen");
        panel.setLayout(null);

        JButton museumButton = createStyledButton("Blather's Museum"); 
        museumButton.setBounds(340,310,280,40);

        JButton nookButton = createStyledButton("Nook's Cranny");
        nookButton.setBounds(340,375,280,40);

        museumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(mainContainer,"MUSEUM_SCREEN");
            }
        });

        nookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(mainContainer,"NOOK_SCREEN");
            }
        });

        panel.add(museumButton);
        panel.add(nookButton);
        return panel;
    }


    private JPanel createMuseumScreen(){
        CardLayout blathersLayout = new CardLayout();
        JPanel blathersContainer = new JPanel(blathersLayout);
        JPanel sortingCard = createBlathersSortingScreen(blathersLayout, blathersContainer);
        blathersContainer.add(sortingCard, "BLATHERS_SORTING");

        return blathersContainer;
    }

    private JPanel createBlathersSortingScreen(CardLayout blathersLayout, JPanel blathersContainer){
        BackgroundPanel panel = new BackgroundPanel("museumBg");
        panel.setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        topBar.setBackground(new Color(255,250,228));

        fileComboBoxBlathers = new JComboBox<String>(CREATURE_FILES);
        fileComboBoxBlathers.setFont(new Font("Helvetica", Font.PLAIN, 14));
        fileComboBoxBlathers.setSelectedIndex(-1);
        
        loadButtonBlathers = createStyledButton("Load");
        
        sortTypeLabelBlathers = new JLabel("Select a file and click Load!");
        sortTypeLabelBlathers.setFont(new Font("Helvetica", Font.BOLD, 18));
        sortTypeLabelBlathers.setForeground(new Color(133, 114, 85));

        fileComboBoxBlathers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String selected = (String) fileComboBoxBlathers.getSelectedItem();
                if(selected == null) return;
                if(selected.toLowerCase().contains("creature")){
                    sortTypeLabelBlathers.setText("Sort: Museum Sort");
                }
                else{
                    sortTypeLabelBlathers.setText("Select a file and click Load!");
                }
            }
        });

        JLabel promptLabel = new JLabel("Input File:");
        promptLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        promptLabel.setForeground(new Color(133, 114, 85));
        
        topBar.add(promptLabel);
        topBar.add(fileComboBoxBlathers);
        topBar.add(loadButtonBlathers);
        topBar.add(sortTypeLabelBlathers);
        panel.add(topBar, BorderLayout.NORTH);

        JPanel workArea = new JPanel(null);
        workArea.setOpaque(false);

        arrayGridBlathers = new JPanel();
        arrayGridBlathers.setOpaque(false);
        arrayGridBlathers.setBounds(10,120,930,100);
        workArea.add(arrayGridBlathers);

        actionButtonBlathers = createStyledButton("Sort");
        actionButtonBlathers.setBounds(380,280,200,40);
        actionButtonBlathers.setVisible(false);
        workArea.add(actionButtonBlathers);

        JButton backToSelectionScreen = createStyledButton("Back");
        backToSelectionScreen.setBounds(15, 390, 100, 40);
        workArea.add(backToSelectionScreen);

        panel.add(workArea, BorderLayout.CENTER);

        loadButtonBlathers.addActionListener(e -> handleLoadBlathers());
        actionButtonBlathers.addActionListener(e -> handleSortBlathers());
        backToSelectionScreen.addActionListener(e -> cardLayout.show(mainContainer, "SELECT_SCREEN"));
        
        return panel;
    }   

    private void handleLoadBlathers(){
        String filename = (String) fileComboBoxBlathers.getSelectedItem();
        if(filename == null) return;

        backendEngine.readData(filename);
        Creature[] creatures = backendEngine.getCreatures();

        arrayGridBlathers.removeAll();
        int totalElements = creatures.length;
        arrayGridBlathers.setLayout(new GridLayout(1, totalElements, 8,0));
        slotsBlathers = new JLabel[totalElements];

        for (int i = 0; i < totalElements; i++) {
            final String textValue = String.valueOf(creatures[i].getName());

            slotsBlathers[i] = new JLabel(textValue, SwingConstants.CENTER){
                @Override
                protected void paintComponent(Graphics g){
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                    
                    g2.setStroke(new BasicStroke(4));
                    g2.setColor(new Color(60, 45, 30));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };

            slotsBlathers[i].setFont(new Font("Helvetica", Font.BOLD, 12));
            slotsBlathers[i].setForeground(new Color(80, 75, 70));

            if (creatures[i].getType() == 'F'){
                slotsBlathers[i].setBackground(new Color(173, 216, 230));
            }else if (creatures[i].getType() == 'I'){
                slotsBlathers[i].setBackground(new Color(169, 224, 159));
            }else{
                slotsBlathers[i].setBackground(Color.WHITE);
            }

            arrayGridBlathers.add(slotsBlathers[i]);
        }

        actionButtonBlathers.setVisible(true);
        actionButtonBlathers.setText("Sort");
        isFileLoadedBlathers = true;
        arrayGridBlathers.revalidate();
        arrayGridBlathers.repaint();
    }

    private void handleSortBlathers(){
        if(!isFileLoadedBlathers) return;
            
        Creature[] creatures = backendEngine.getCreatures();
        if(creatures == null || slotsBlathers == null) return;

            backendEngine.museumSort();

            for (int i = 0; i < creatures.length; i++) {
                slotsBlathers[i].setText(String.valueOf(creatures[i].getName()));

                    if (creatures[i].getType() == 'F'){
                    slotsBlathers[i].setBackground(new Color(173, 216, 230));
                }else if (creatures[i].getType() == 'I'){
                    slotsBlathers[i].setBackground(new Color(169, 224, 159));
                }else{
                    slotsBlathers[i].setBackground(Color.WHITE);
                }

            }
    }


    private JPanel createNookScreen(){
        CardLayout nookLayout = new CardLayout();

        JPanel nookContainer = new JPanel(nookLayout);
        JPanel sortingCard = createNookSortingScreen(nookLayout, nookContainer);

        nookContainer.add(sortingCard, "NOOK_SORTING");

        return nookContainer;
    }

    private JPanel createNookSortingScreen(CardLayout nookLayout, JPanel nookContainer){
        BackgroundPanel panel = new BackgroundPanel("nookBg");
        panel.setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        topBar.setBackground(new Color(255,250,228));

        fileComboBox = new JComboBox<String>(CATALOGUE_FILES);
        fileComboBox.setFont(new Font("Helvetica", Font.PLAIN, 14));
        fileComboBox.setSelectedIndex(-1);
        
        loadButton = createStyledButton("Load");
        
        sortTypeLabel = new JLabel("Select a file and click Load!");
        sortTypeLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        sortTypeLabel.setForeground(new Color(133, 114, 85));

        fileComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String selected = (String) fileComboBox.getSelectedItem();
                if(selected == null) return;
                if(selected.toLowerCase().contains("catalog")){
                    sortTypeLabel.setText("Sort: Catalog Sort");
                }
                else{
                    sortTypeLabel.setText("Select a file and click Load!");
                }
            }
        });

        JLabel promptLabel = new JLabel("Input File:");
        promptLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        promptLabel.setForeground(new Color(133, 114, 85));
        
        topBar.add(promptLabel);
        topBar.add(fileComboBox);
        topBar.add(loadButton);
        topBar.add(sortTypeLabel);
        panel.add(topBar, BorderLayout.NORTH);

        JPanel workArea = new JPanel(null);
        workArea.setOpaque(false);

        arrayGrid = new JPanel();
        arrayGrid.setOpaque(false);
        arrayGrid.setBounds(30,120,880,100);
        workArea.add(arrayGrid);

        actionButton = createStyledButton("Sort");
        actionButton.setBounds(380,280,200,40);
        actionButton.setVisible(false);

        isabelleNextButton = createStyledButton("Next");
        isabelleNextButton.setBounds(830,390,100,40);
        isabelleNextButton.setVisible(false);
        isabelleNextButton.setEnabled(false);

        JButton backToSelectionScreen = createStyledButton("Back");
        backToSelectionScreen.setBounds(15, 390, 100, 40);
        workArea.add(backToSelectionScreen);

        workArea.add(actionButton);
        workArea.add(isabelleNextButton);
        panel.add(workArea, BorderLayout.CENTER);

        loadButton.addActionListener(e -> handleNookLoad());
        actionButton.addActionListener(e -> handleNookSort());

        backToSelectionScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(mainContainer,"SELECT_SCREEN");
            }
        });
        
        return panel;
    }   

    private void handleNookLoad(){
        String filename = (String) fileComboBox.getSelectedItem();
        if(filename == null) return;

        backendEngine.readData(filename);

        ArrayList<Catalog> items = backendEngine.getCatalogItems();
        arrayGrid.removeAll();

        int totalElements = items.size();
        arrayGrid.setLayout(new GridLayout(1, totalElements, 8,0));
        slots = new JLabel[totalElements];

        for (int i = 0; i < totalElements; i++) {
            final String textValue = String.valueOf(items.get(i).getPrice());
            slots[i] = new JLabel(textValue, SwingConstants.CENTER){
                @Override
                protected void paintComponent(Graphics g){
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                
                    g2.setStroke(new BasicStroke(4));
                    g2.setColor(new Color(60, 45, 30));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            
            slots[i].setFont(new Font("Helvetica", Font.BOLD, 16));
            slots[i].setForeground(new Color(80, 75, 70));
            slots[i].setBackground(new Color(255, 251, 173));
            arrayGrid.add(slots[i]);
        }

        actionButton.setVisible(true);
        actionButton.setText("Sort");
        isFileLoaded = true;
        arrayGrid.revalidate();
        arrayGrid.repaint();
    }

    private void handleNookSort(){
        if(!isFileLoaded) return;
        
        ArrayList<Catalog> items = backendEngine.getCatalogItems();
        if(items == null || slots == null) return;

        backendEngine.catalogSort();

        for (int i = 0; i < items.size(); i++) {
            slots[i].setText(String.valueOf(items.get(i).getPrice()));
            slots[i].setBackground(new Color(255, 251, 173)); 
        }

        isabelleNextButton.setVisible(true);
        isabelleNextButton.setEnabled(true);
        isabelleNextButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            prepareSearchGrid();
            cardLayout.show(mainContainer, "ISABELLE_SEARCH");
            priceInputFieldIsabelle.setText("");
        }
        });
    }


    private JPanel createIsabelleSearchScreen(){
        BackgroundPanel panel = new BackgroundPanel("isabelleBg");
        panel.setLayout(null);

        searchGrid = new JPanel();
        searchGrid.setOpaque(false);
        searchGrid.setBounds(75, 120, 800, 90);
        panel.add(searchGrid);

        JLabel label = new JLabel("Target Item Price:");
        label.setFont(new Font("Helvetica", Font.BOLD, 20));
        label.setForeground(new Color(133, 114, 85));
        label.setBackground(new Color(255, 250, 229));
        label.setBounds(290, 225, 200, 30);
        label.setOpaque(true);
        panel.add(label);

        priceInputFieldIsabelle = new JTextField();
        priceInputFieldIsabelle.setFont(new Font("Helvetica", Font.BOLD, 20));
        priceInputFieldIsabelle.setBounds(490, 225, 120, 30);
        panel.add(priceInputFieldIsabelle);

        searchButtonIsabelle = createStyledButton("Search");
        searchButtonIsabelle.setBounds(380,280,200,40);
        searchButtonIsabelle.addActionListener(e -> handleBinarySearch());
        panel.add(searchButtonIsabelle);

        JButton backToNookScreen = createStyledButton("Back");
        backToNookScreen.setBounds(15, 450, 100, 40);
        panel.add(backToNookScreen);
        backToNookScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(mainContainer,"NOOK_SCREEN");
            }
        });

        return panel;
    }

    private JPanel createIsabelleFoundScreen(){
        BackgroundPanel panel = new BackgroundPanel("isabelleBg");
        panel.setLayout(null);

        JPanel highlightContainer = new JPanel(new GridLayout(1,1));
        highlightContainer.setOpaque(false);
        highlightContainer.setBounds(380, 200, 210, 90);

        JLabel foundSlot = new JLabel("", SwingConstants.CENTER){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setStroke(new BasicStroke(4));
                g2.setColor(new Color(60, 45, 30));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        foundSlot.setFont(new Font("Helvetica", Font.BOLD, 22));
        foundSlot.setForeground(new Color(133, 114, 85));
        foundSlot.setBackground(new Color(255, 251, 173));
        highlightContainer.add(foundSlot);
        panel.add(highlightContainer);

        panel.putClientProperty("targetLabel", foundSlot);

        JButton backToIsabelleSearch = createStyledButton("Back");
        backToIsabelleSearch.setBounds(15, 450, 100, 40);
        panel.add(backToIsabelleSearch);
        backToIsabelleSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                priceInputFieldIsabelle.setText("");
                cardLayout.show(mainContainer, "ISABELLE_SEARCH");
            }
        });

        return panel;
    }

    private void prepareSearchGrid(){
        searchGrid.removeAll();

        ArrayList<Catalog> items = backendEngine.getCatalogItems();
        if(items == null) return;

        int size = items.size();
        searchGrid.setLayout(new GridLayout(1, size, 8, 0));
        searchSlots = new JLabel[size];

        for(int i =0; i < size; i++){
            int itemPrice = items.get(i).getPrice();

            searchSlots[i] = new JLabel(String.valueOf(itemPrice), SwingConstants.CENTER){
                @Override
                protected void paintComponent(Graphics g){
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                    
                    g2.setStroke(new BasicStroke(4));
                    g2.setColor(new Color(60, 45, 30));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };

            searchSlots[i].setFont(new Font("Helvetica", Font.BOLD, 16));
            searchSlots[i].setForeground(new Color(133, 114, 85));
            searchSlots[i].setBackground(new Color(255, 251, 173));
            searchGrid.add(searchSlots[i]);
        }

        searchGrid.revalidate();
        searchGrid.repaint();
    }

    private void handleBinarySearch(){
        ArrayList<Catalog> items = backendEngine.getCatalogItems();
        if(items == null || items.isEmpty()) return;

        int targetPrice;
        try{
            targetPrice = Integer.parseInt(priceInputFieldIsabelle.getText().trim());
        }
        catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(mainContainer, "Please input a valid whole number value.", "Input Error", JOptionPane.ERROR_MESSAGE);
            priceInputFieldIsabelle.setText("");
            return;
        }

        Catalog foundItem = backendEngine.findItem(targetPrice);

        if(foundItem != null){
            searchButtonIsabelle.setText("Search");
            searchButtonIsabelle.repaint();

            for(Component comp : mainContainer.getComponents()){
                if(comp instanceof JPanel && ((JPanel)comp).getClientProperty("targetLabel") != null){
                    JLabel targetLabel = (JLabel)((JPanel)comp).getClientProperty("targetLabel");
                    targetLabel.setText(String.valueOf(foundItem.getName()));
                    break;
                }
            }
            cardLayout.show(mainContainer, "ISABELLE_FOUND");
        }else{
            JOptionPane.showMessageDialog(mainContainer, "Item price could not be located in catalog records.", "Search Error", JOptionPane.WARNING_MESSAGE);
            priceInputFieldIsabelle.setText("");
        }
    }


    private JButton createStyledButton(String text){
        JButton button = new JButton(text){

            @Override
            protected void paintComponent(Graphics g){

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(103, 172, 149));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setStroke(new BasicStroke(3));
                g2.setColor(new Color(75,130,112));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 25, 25);
                super.paintComponent(g);
                g2.dispose();
            }    
        };

        button.setFont(new Font("Source Sans 3",Font.BOLD,18));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }


    private class BackgroundPanel extends JPanel{
        private ImageIcon bgIcon;
        private String bgPhase;

        public BackgroundPanel(String phase){
            this.bgPhase = phase;
            String imgPath = "";

            if(bgPhase.equals("titleScreen")){
                imgPath = "./assets/titleScreen.png";
            }
            else if(bgPhase.equals("museumBg")){
                imgPath = "assets/museumBg.png";
            }
            else if(bgPhase.equals("nookBg")){
                imgPath = "assets/nooksCrannyBg.png";
            }
            else if(bgPhase.equals("isabelleBg")){
                imgPath = "assets/isabelleBg.png";
            }

            if(!imgPath.isEmpty()){
                bgIcon = new ImageIcon(imgPath);
            }
            else{
                System.out.println("Error: Could not locate background asset(s).");
            }
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            if(bgIcon != null && bgIcon.getImage() != null){
                g.drawImage(bgIcon.getImage(), 0 ,0 ,getWidth() ,getHeight() ,this);
            }
        }
    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new Driver());
    }
}
