package PipelineGame.ui.segments;

import PipelineGame.model.events.IPipelineSegmentListener;
import PipelineGame.model.pipeline.devices.WaterDevice;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.pipeline.water.WaterProperty;
import PipelineGame.ui.utils.ImageHelper;
import PipelineGame.ui.WaterView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static PipelineGame.AppSettings.DEFAULT_SEGMENT_SIDE_SIZE;
import static PipelineGame.ui.DevicesView.drawDevicesIcons;
import static PipelineGame.ui.utils.ImageHelper.getScaledIcon;

public abstract class SegmentView extends JButton {
    public static int SIDE_SIZE = DEFAULT_SEGMENT_SIDE_SIZE;
    public Segment segment;

    public ImageIcon rawSegmentIcon;

    private boolean isBlinking;

    public SegmentView(Segment segment) {
        this.segment = segment;
        setIcon(getScaledIcon(createImage(), new Dimension(SIDE_SIZE, SIDE_SIZE)));

        setBorder(BorderFactory.createEmptyBorder());
        setBackground(Color.DARK_GRAY);
        addActionListener(e -> rotateRight());

        segment.addPipelineSegmentListener(new PipelineSegmentListener());
        this.setToolTipText(this.createTooltipTextForSegment());
    }

    private String createTooltipTextForSegment() {
        if (this.segment.getDevices().size() == 0) {
            return "<html><b>Нет устройств</b><br>";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>").append("<b>Устройства:</b><br>");
        for (WaterDevice device : this.segment.getDevices()) {
            stringBuilder.append("\t<i>").append(device.toString()).append("</i><br>");
        }
        return stringBuilder.toString();
    }

    private String createTooltipTextForSegmentAndWater() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.createTooltipTextForSegment()).append("<html><b>Вода:</b><br>");
        for (WaterProperty p : this.segment.getWater().getPropertyContainer()) {
            stringBuilder.append("\t<i>").append(p.toString()).append("</i><br>");
        }
        return stringBuilder.toString();
    }

    public void setEnabledSegment(boolean isEnabled) {
        if (isEnabled) {
            addActionListener(e -> rotateRight());
        }
        else {
            for( ActionListener al : this.getActionListeners() ) {
                this.removeActionListener( al );
            }
        }
    }

    public void rotateRight() {
        this.segment.rotateRight();
        this.setIcon(ImageHelper.rotateIcon(this.rawSegmentIcon));
    }

    public void pourWater() {
        this.startBlinking();
        this.setToolTipText(this.createTooltipTextForSegmentAndWater());
    }

    protected void conductWater() {
        setBackground(new WaterView(this.segment.getWater()).getColor());
        this.setToolTipText(this.createTooltipTextForSegmentAndWater());
    }

    @Override
    public void setIcon(Icon defaultIcon) {
        this.rawSegmentIcon = (ImageIcon) defaultIcon;
        super.setIcon(drawDevicesIcons(this.rawSegmentIcon, segment.getDevices()));
    }

    protected abstract ImageIcon createImage();

    private void startBlinking() {
        if (isBlinking) {
            return;
        }
        isBlinking = true;

        new Timer(333, new ActionListener() {
            private boolean toggle = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggle) {
                    setBackground(new WaterView(segment.getWater()).getColor());
                } else {
                    setBackground(Color.WHITE);
                }
                toggle = !toggle;
            }
        }).start();
    }

    // ---------------------------------- Слушатель модели сегмента ---------------------------------------------------

    private class PipelineSegmentListener implements IPipelineSegmentListener {

        @Override
        public void onConductWater() {
            conductWater();
        }

        @Override
        public void onSegmentRotate() {
        }
    }
}
