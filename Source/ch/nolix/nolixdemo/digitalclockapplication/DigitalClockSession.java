package ch.nolix.nolixdemo.digitalclockapplication;

import ch.nolix.core.programcontrol.sequencer.Sequencer;
import ch.nolix.system.application.guiapplication.BackendGUIClientSession;
import ch.nolix.system.application.main.VoidApplicationContext;
import ch.nolix.system.gui.widget.Label;
import ch.nolix.system.time.base.Time;

final class DigitalClockSession extends BackendGUIClientSession<VoidApplicationContext> {
	
	private static final int TIME_UPDATE_INTERVAL_IN_MILLISECONDS = 200;
	
	private final Label timeLabel = new Label().setId(WidgetIdCatalogue.TIME_LABEL_ID);
	
	@Override
	protected void initializeBaseBackGUIClientSession() {
		
		getRefGUI()
		.setConfiguration(LookCreator.INSTANCE.createLook())
		.pushLayer(timeLabel);
		
		Sequencer
		.asLongAs(this::isOpen)
		.afterAllMilliseconds(TIME_UPDATE_INTERVAL_IN_MILLISECONDS)
		.runInBackground(this::updateTime);
	}
	
	private String getCurrentTimeText() {
		
		final var currentTime = Time.fromCurrentTime();
		
		return
		String.format(
			"%02d:%02d:%02d",
			currentTime.getHourOfDay(),
			currentTime.getMinuteOfHour(),
			currentTime.getSecondOfMinute()
		);
	}
	
	private void updateTime() {
		
		timeLabel.setText(getCurrentTimeText());
		
		updateCounterpart();
	}
}